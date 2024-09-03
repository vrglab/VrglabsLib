package org.Vrglab.AutoRegisteration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.Vrglab.AutoRegisteration.Annotations.*;
import org.Vrglab.AutoRegisteration.Objects.*;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IAutoLoadResolver;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Utils.Utils;
import org.Vrglab.Utils.VLModInfo;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Supplier;

public class AutoRegistryLoader {

    static ClassLoader loader = null;

    public static void LoadAllInPackage(String packageName, String modid) {
        loadItemsInPackage(packageName, modid);
        loadBlocksInPackage(packageName, modid);
        loadItemlessBlocksInPackage(packageName, modid);
        loadBlockEntityTypesInPackage(packageName, modid);
        callInitsInPackage(packageName, modid);
        loadCMTInPackage(packageName, modid);
    }

    public static void loadItemsInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItem.class, RegistryItem.class, (rg, rt) -> {
            Object return_val = Registry.RegisterItem(rt.ItemName(), modId, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlock.class, RegistryBlock.class, (rg, rt) -> {
            Object return_val = Registry.RegisterBlock(rt.Name(), modId, rg.getSupplier(), (Supplier<Item.Settings>)rg.getArgs().get("item.settings"));
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadItemlessBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItemlessBlock.class, RegistryItemlessBlock.class, (rg, rt) -> {
            Object return_val = Registry.RegisterItemlessBlock(rt.Name(), modId, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlockEntityTypesInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlockEntityType.class, RegistryBlockEntityType.class, (rg, rt) -> {
            Object return_val = Registry.RegisterBlockEntityType(
                    rt.Name(),
                    modId,
                    (IBlockEntityLoaderFunction) rg.getArgs().get("new"),
                    (rg.getArgs().get("block") instanceof RegistryBlock) ? (entityTypeBlockSelector.accept(rg)) : rg.getArgs().get("block")
            );
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    private static void callInitsInPackage(String packageName, String modId) {
        Set<Class> annotatedType = getTypesAnnotatedIn(InitializableClass.class, packageName, modId);
        annotatedType.forEach(type -> {
            try {
                type.getMethod("init").setAccessible(true);
                type.getMethod("init").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                VLModInfo.LOGGER.error(e.getMessage());
            }
        });
    }

    public static void loadCMTInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterCMT.class, RegistryCMT.class, (rg, rt) -> {
            Object return_val = Registry.RegisterCreativeModeTab(rt.Name(), modId, (ItemGroup)rg.getRawData());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    private static <T extends Annotation, B extends AutoRegisteryObject> void LoadingResolver(String packageName, String modId, Class<T> annotation, Class<B> registry, IAutoLoadResolver<B, T> Resolver) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(annotation, packageName, modId);
        annotatedFields.forEach(field -> {
            T anno = field.getAnnotation(annotation);
            try {
                field.setAccessible(true);
                B rg = ((B)field.get(null));
                if(rg.getModid().equals(modId))
                    Resolver.accept(rg, anno);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    /** HELPER METHODS AND REFLECTION FUNCTIONS **/

    private static Set<Field> getFieldsAnnotatedIn(Class annotation, String packageName, String modid){
        return (Set<Field>)collectAnnotatedFieldsForMod.accept(packageName, annotation, modid);
    }

    private static Set<Class> getTypesAnnotatedIn(Class annotation, String packageName, String modid){
        return (Set<Class>) collectAnnotatedTypesForMod.accept(packageName, annotation, modid);
    }

    @ApiStatus.Internal
    public static ICallBack collectAnnotatedTypesForMod;
    @ApiStatus.Internal
    public static ICallBack collectAnnotatedFieldsForMod;
    @ApiStatus.Internal
    public static ICallBack entityTypeBlockSelector;
}
