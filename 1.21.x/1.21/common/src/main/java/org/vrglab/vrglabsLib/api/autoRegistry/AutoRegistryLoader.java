package org.vrglab.vrglabsLib.api.autoRegistry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.Utils.Utils;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.*;
import org.vrglab.vrglabsLib.api.autoRegistry.World.*;
import org.vrglab.vrglabsLib.api.registries.Registry;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.core.Constants;
import org.jetbrains.annotations.ApiStatus;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoRegistryLoader {

    static ClassLoader loader = null;

    static AtomicInteger successFullyLoadedContentCount = new AtomicInteger(0);

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
            CreativeModeTab autoRegTab = Utils.typeCaster(args[0], CreativeModeTab.class);
            RegisterCreativeModeTab rt = Utils.typeCaster(args[1], RegisterCreativeModeTab.class);

            autoRegTab.setId(ResourceLocation.fromNamespaceAndPath(autoRegTab.getModid(), rt.Name()));
            Object return_val = Registry.RegisterCreativeModeTab(rt.Name(), autoRegTab.getModid(), autoRegTab.getSupplier());
            autoRegTab.setRegistryData(return_val);
            successFullyLoadedContentCount.getAndIncrement();
            return return_val;
        });
    }

    public static void loadItemsInPackage(String packageName, String modId) {

        LoadingResolver(packageName, modId, RegisterItem.class, (args) -> {
            Item<?> autoRegItem = Utils.typeCaster(args[0], Item.class);
            RegisterItem rt = Utils.typeCaster(args[1], RegisterItem.class);
            autoRegItem.setId(ResourceLocation.fromNamespaceAndPath(autoRegItem.getModid(), rt.ItemName()));

            Object return_val = null;

            if (autoRegItem.getArgs().containsKey("item.class")) {
                return_val = Registry.RegisterItem(rt.ItemName(), modId, Utils.typeCasterIClampedCallBackafied(autoRegItem.getArgs().get("item"), net.minecraft.world.item.Item.class),
                        Utils.typeCasterSupplierfied(autoRegItem.getArgs().get("settingsItem"), net.minecraft.world.item.Item.Properties.class),
                        Utils.typeCaster(autoRegItem.getArgs().get("item.class")));
            } else {
                return_val = Registry.RegisterItem(rt.ItemName(), modId, Utils.typeCasterIClampedCallBackafied(autoRegItem.getArgs().get("item"), net.minecraft.world.item.Item.class),
                        Utils.typeCasterSupplierfied(autoRegItem.getArgs().get("settingsItem"), net.minecraft.world.item.Item.Properties.class));
            }


            autoRegItem.setRegistryData(return_val);
            successFullyLoadedContentCount.getAndIncrement();
            return return_val;
        });
    }

    public static void loadBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlock.class, (args) -> {
            Block<?> autoRegBlock = Utils.typeCaster(args[0], Block.class);
            RegisterBlock rt = Utils.typeCaster(args[1], RegisterBlock.class);
            autoRegBlock.setId(ResourceLocation.fromNamespaceAndPath(autoRegBlock.getModid(), rt.Name()));
            Object return_val = Registry.RegisterBlock(rt.Name(), modId,
                    Utils.typeCasterIClampedSingleCallBackafied(autoRegBlock.getArgs().get("block"), net.minecraft.world.level.block.Block.class, BlockBehaviour.Properties.class),
                    Utils.typeCasterSupplierfied(autoRegBlock.getArgs().get("item.settings"), net.minecraft.world.item.Item.Properties.class),
                    Utils.typeCasterSupplierfied(autoRegBlock.getArgs().get("block.settings"), net.minecraft.world.level.block.state.BlockBehaviour.Properties.class));
            autoRegBlock.setRegistryData(return_val);
            successFullyLoadedContentCount.getAndIncrement();
            return return_val;
        });
    }

    public static void loadItemlessBlocksInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterItemlessBlock.class, (args) -> {
            Block<?> autoRegBlock = Utils.typeCaster(args[0], Block.class);
            RegisterItemlessBlock rt = Utils.typeCaster(args[1], RegisterItemlessBlock.class);
            autoRegBlock.setId(ResourceLocation.fromNamespaceAndPath(autoRegBlock.getModid(), rt.Name()));

            Object return_val = Registry.
                    RegisterItemlessBlock(rt.Name(), modId,
                            Utils.typeCasterIClampedSingleCallBackafied(autoRegBlock.getArgs().get("block"), net.minecraft.world.level.block.Block.class, BlockBehaviour.Properties.class),
                            Utils.typeCasterSupplierfied(autoRegBlock.getArgs().get("block.settings"), net.minecraft.world.level.block.state.BlockBehaviour.Properties.class));
            autoRegBlock.setRegistryData(return_val);
            successFullyLoadedContentCount.getAndIncrement();
            return return_val;
        });
    }

    public static void loadBlockEntityTypesInPackage(String packageName, String modId) {
        LoadingResolver(packageName, modId, RegisterBlockEntityType.class, (args) -> {
            BlockEntity<?> autoRegBlockEntity = Utils.typeCaster(args[0], BlockEntity.class);
            RegisterBlockEntityType rt = Utils.typeCaster(args[1], RegisterBlockEntityType.class);
            autoRegBlockEntity.setId(ResourceLocation.fromNamespaceAndPath(autoRegBlockEntity.getModid(), rt.Name()));
            Object return_val = Registry.RegisterBlockEntityType(
                    rt.Name(),
                    modId,
                    Utils.typeCaster(autoRegBlockEntity.getArgs().get("new"), IBlockEntityLoaderFunction.class),
                    (autoRegBlockEntity.getArgs().get("block") instanceof Block<?>) ? (entityTypeBlockSelector.accept(autoRegBlockEntity)) : autoRegBlockEntity.getArgs().get("block")
            );
            autoRegBlockEntity.setRegistryData(return_val);
            successFullyLoadedContentCount.getAndIncrement();
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

    private static <T extends Annotation> void LoadingResolver(String packageName, String modId, Class<T> annotation, ICallBack resolver) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(annotation, packageName, modId);
        annotatedFields.forEach(field -> {
            Annotation anno = field.getAnnotation(annotation);
            try {
                AutoRegistryObject<?> rg = Utils.typeCaster(field.get(null), AutoRegistryObject.class);
                if (rg.getModid().equals(modId)) {
                    resolver.accept(rg, anno);
                }
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static int getSuccessFullyLoadedContentCount() {
        return successFullyLoadedContentCount.get();
    }

    /** HELPER METHODS AND REFLECTION FUNCTIONS **/

    private static Set<Field> getFieldsAnnotatedIn(Class annotation, String packageName, String modid){
        return (Set<Field>) collectAnnotatedFieldsForMod.accept(packageName, annotation, modid);
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
