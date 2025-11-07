package org.vrglab.vrglabsLib.api.autoRegistry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.*;
import org.vrglab.vrglabsLib.api.autoRegistry.World.*;
import org.vrglab.vrglabsLib.api.callbacks.IClampedSingleCallback;
import org.vrglab.vrglabsLib.api.registries.Registry;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.core.Constants;
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
        loadCreativeModeTabsInPackage(packageName, modid);
        callInitsInPackage(packageName, modid);
    }


    public static void loadCreativeModeTabsInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterCreativeModeTab.class, (args) -> {
            CreativeModeTab rg = ((CreativeModeTab)args[0]);
            RegisterCreativeModeTab rt = ((RegisterCreativeModeTab)args[1]);
            rg.setId(ResourceLocation.fromNamespaceAndPath(rg.getModid(), rt.Name()));
            Object return_val = Registry.RegisterCreativeModeTab(rt.Name(), rg.getModid(), rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadItemsInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItem.class, (args) -> {
            Item rg = ((Item)args[0]);
            RegisterItem rt = ((RegisterItem)args[1]);
            rg.setId(ResourceLocation.fromNamespaceAndPath(rg.getModid(), rt.ItemName()));
            Object return_val = Registry.RegisterItem(rt.ItemName(), modId, ((IClampedCallBack<net.minecraft.world.item.Item>) rg.getArgs().get("item")), (Supplier<net.minecraft.world.item.Item.Properties>)rg.getArgs().get("settingsItem"));
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlock.class, (args) -> {
            Block rg = ((Block)args[0]);
            RegisterBlock rt = ((RegisterBlock)args[1]);
            rg.setId(ResourceLocation.fromNamespaceAndPath(rg.getModid(), rt.Name()));
            Object return_val = Registry.RegisterBlock(rt.Name(), modId,
                    (IClampedSingleCallback<net.minecraft.world.level.block.Block, BlockBehaviour.Properties>) rg.getArgs().get("block"),
                    (Supplier<net.minecraft.world.item.Item.Properties>)rg.getArgs().get("item.settings"), (Supplier<BlockBehaviour.Properties>)rg.getArgs().get("block.settings"));
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadItemlessBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItemlessBlock.class, (args) -> {
            ItemlessBlock rg = ((ItemlessBlock)args[0]);
            RegisterItemlessBlock rt = ((RegisterItemlessBlock)args[1]);
            rg.setId(ResourceLocation.fromNamespaceAndPath(rg.getModid(), rt.Name()));
            Object return_val = Registry.RegisterItemlessBlock(rt.Name(), modId, (IClampedCallBack<net.minecraft.world.level.block.Block>) rg.getArgs().get("block"), (Supplier<BlockBehaviour.Properties>) rg.getArgs().get("block.settings"));
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    public static void loadBlockEntityTypesInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlockEntityType.class, (args) -> {
            BlockEntity rg = ((BlockEntity)args[0]);
            RegisterBlockEntityType rt = ((RegisterBlockEntityType)args[1]);
            rg.setId(ResourceLocation.fromNamespaceAndPath(rg.getModid(), rt.Name()));
            Object return_val = Registry.RegisterBlockEntityType(
                    rt.Name(),
                    modId,
                    (IBlockEntityLoaderFunction) rg.getArgs().get("new"),
                    (rg.getArgs().get("block") instanceof Block<?>) ? (entityTypeBlockSelector.accept(rg)) : rg.getArgs().get("block")
            );
            rg.setRegistryData(return_val);
            return return_val;
        });
    }

    private static void callInitsInPackage(String packageName, String modId) {
        Set<Class> annotatedType = getTypesAnnotatedIn(InitializableClass.class, packageName, modId);
        annotatedType.forEach(type -> {
            try {
                type.getMethod("init").invoke(null);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                Constants.LOG.error(e.getMessage());
            }
        });
    }

    private static <T extends Annotation> void LoadingResolver(String packageName, String modId, Class<T> annotation, ICallBack Resolver) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(annotation, packageName, modId);
        annotatedFields.forEach(field -> {
            Annotation anno = field.getAnnotation(annotation);
            try {
                AutoRegistryObject rg = ((AutoRegistryObject)field.get(null));
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
