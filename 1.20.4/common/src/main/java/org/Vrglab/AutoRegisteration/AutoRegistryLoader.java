package org.Vrglab.AutoRegisteration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.Vrglab.AutoRegisteration.Annotations.*;
import org.Vrglab.AutoRegisteration.Objects.*;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallbackVoid;
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
    }

    public static void loadItemsInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItem.class, (args) -> {
            RegistryItem rg = ((RegistryItem)args[0]);
            RegisterItem rt = ((RegisterItem)args[1]);
            Object return_val = Registry.RegisterItem(rt.ItemName(), modId, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlock.class, (args) -> {
            RegistryBlock rg = ((RegistryBlock)args[0]);
            RegisterBlock rt = ((RegisterBlock)args[1]);
            Object return_val = Registry.RegisterBlock(rt.Name(), modId, rg.getSupplier(), (Supplier<Item.Settings>)rg.getArgs().get("item.settings"));
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadItemlessBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItemlessBlock.class, (args) -> {
            RegistryItemlessBlock rg = ((RegistryItemlessBlock)args[0]);
            RegisterItemlessBlock rt = ((RegisterItemlessBlock)args[1]);
            Object return_val = Registry.RegisterItemlessBlock(rt.Name(), modId, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlockEntityTypesInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlockEntityType.class, (args) -> {
            RegistryBlockEntityType rg = ((RegistryBlockEntityType)args[0]);
            RegisterBlockEntityType rt = ((RegisterBlockEntityType)args[1]);
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
        // this function should probably be deleted
        /*Set<Class> annotatedType = getTypesAnnotatedIn(AwakenOnLoad.class, packageName, modId);
        annotatedType.forEach(type -> {
            try {
                type.getMethod("init").invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });*/
    }

    private static <T extends Annotation> void LoadingResolver(String packageName, String modId, Class<T> annotation, ICallBack Resolver) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(annotation, packageName, modId);
        annotatedFields.forEach(field -> {
            Annotation anno = field.getAnnotation(annotation);
            try {
                AutoRegisteryObject rg = ((AutoRegisteryObject)field.get(null));
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
