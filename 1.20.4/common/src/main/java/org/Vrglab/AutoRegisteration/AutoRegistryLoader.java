package org.Vrglab.AutoRegisteration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.Vrglab.AutoRegisteration.Annotations.AwakenOnLoad;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBlock;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBlockEntityType;
import org.Vrglab.AutoRegisteration.Annotations.RegisterItem;
import org.Vrglab.AutoRegisteration.Objects.RegistryBlock;
import org.Vrglab.AutoRegisteration.Objects.RegistryBlockEntityType;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;
import org.Vrglab.Modloader.Types.ICallBack;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.function.Supplier;

public class AutoRegistryLoader {

    static ClassLoader loader = null;

    public static void LoadAllInPackage(String packageName, String modid) {
        loadItemsInPackage(packageName, modid);
        loadBlocksInPackage(packageName, modid);
        loadBlockEntityTypesInPackage(packageName, modid);
        callInitsInPackage(packageName, modid);
    }

    public static void LoadAllInPackage(String packageName, String modid, ClassLoader _loader) {
        loader = _loader;
        loadBlocksInPackage(packageName, modid);
    }

    private static void loadItemsInPackage(String packageName, String modId) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(RegisterItem.class, packageName, modId);
        annotatedFields.forEach(field -> {
            RegisterItem annotation = field.getAnnotation(RegisterItem.class);
            Registry.RegisterItem(annotation.ItemName(), modId, new Supplier<Item>() {
                @Override
                public Item get() {
                    try {
                        return (Item)field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        });
    }

    private static void loadBlocksInPackage(String packageName, String modId) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(RegisterBlock.class, packageName, modId);
        annotatedFields.forEach(field -> {
            RegisterBlock annotation = field.getAnnotation(RegisterBlock.class);
            try {
                field.setAccessible(true);
                RegistryBlock rg = ((RegistryBlock)field.get(null));
                Registry.RegisterBlock(annotation.Name(), modId, new Supplier<Block>() {
                    @Override
                    public Block get() {
                        if(rg.getRegisteredObject() == null)
                            rg.setRegisteredObject();
                      return (Block) rg.getRegisteredObject();
                    }
                }, (Supplier<Item.Settings>)rg.getArgs().get("item.settings"));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void loadBlockEntityTypesInPackage(String packageName, String modId) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(RegisterBlockEntityType.class, packageName, modId);
        annotatedFields.forEach(field -> {
            RegisterBlockEntityType annotation = field.getAnnotation(RegisterBlockEntityType.class);
            try {
                RegistryBlockEntityType rg = ((RegistryBlockEntityType)field.get(null));
                rg.setRegistryData(Registry.RegisterBlockEntityType(annotation.Name(), modId, (IBlockEntityLoaderFunction) rg.getArgs().get("new"), rg.getArgs().get("block")));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void callInitsInPackage(String packageName, String modId) {
        Set<Class> annotatedType = getTypesAnnotatedIn(AwakenOnLoad.class, packageName, modId);
        annotatedType.forEach(type -> {
            try {
                type.getMethod("init").invoke(null);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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

    public static ICallBack collectAnnotatedTypesForMod;
    public static ICallBack collectAnnotatedFieldsForMod;
}
