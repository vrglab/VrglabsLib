package org.Vrglab.AutoRegisteration;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.gen.GenerationStep;
import org.TestSystem.TestMod;
import org.Vrglab.AutoRegisteration.Annotations.*;
import org.Vrglab.AutoRegisteration.Objects.*;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.*;
import org.Vrglab.Modloader.enumTypes.*;
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

        /** ITEMS **/
        LoadingResolver(packageName, modid, RegisterItem.class, RegistryItem.class, (rg, rt) -> {
            Object return_val = Registry.RegisterItem(rt.ItemName(), modid, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** BLOCKS **/
        LoadingResolver(packageName, modid, RegisterBlock.class, RegistryBlock.class, (rg, rt) -> {
            Object return_val = Registry.RegisterBlock(rt.Name(), modid, rg.getSupplier(), (Supplier<Item.Settings>)rg.getArgs().get("item.settings"));
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** ITEMLESS BLOCKS **/
        LoadingResolver(packageName, modid, RegisterItemlessBlock.class, RegistryItemlessBlock.class, (rg, rt) -> {
            Object return_val = Registry.RegisterItemlessBlock(rt.Name(), modid, rg.getSupplier());
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** BLOCK ENTITIES **/
        LoadingResolver(packageName, modid, RegisterBlockEntityType.class, RegistryBlockEntityType.class, (rg, rt) -> {
            Object return_val = Registry.RegisterBlockEntityType(
                    rt.Name(),
                    modid,
                    (IBlockEntityLoaderFunction) rg.getArgs().get("new"),
                    (rg.getArgs().get("block") instanceof RegistryBlock) ? (entityTypeBlockSelector.accept(rg)) : rg.getArgs().get("block")
            );
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** POI **/
        LoadingResolver(packageName, modid, RegisterPOI.class, RegistryPOI.class, (rg, rt) -> {
            Object return_val = Registry.RegisterPOI(rt.Name(), modid, ((RegistryBlock<?>)rg.getArgs().get("block")).getRawData(), ((int)rg.getArgs().get("tickcount")), ((int)rg.getArgs().get("searchdistance")));
            rg.setRegistryData(return_val);
            rg.setId(rt.Name());
            return return_val;
        });

        /** PROFESSION **/
        LoadingResolver(packageName, modid, RegisterProfession.class, RegistryProfession.class, (rg, rt) -> {
            RegistryBlock[] rg_blocks = ((RegistryBlock<?>[])rg.getArgs().get("immutableBlocks"));
            RegistryItem[] rg_items = ((RegistryItem[])rg.getArgs().get("immutableItems"));
            RegistryPOI poi = ((RegistryPOI)rg.getArgs().get("poi"));

            Item[] immutableItems = new Item[rg_blocks.length];
            Block[] immutableBlocks = new Block[rg_blocks.length];

            for (int i = 0; i < rg_items.length; i++) {
                immutableItems[i] = (Item)rg_items[i].getRawData();
            }
            for (int i = 0; i < rg_blocks.length; i++) {
                immutableBlocks[i] = (Block)rg_blocks[i].getRawData();
            }

            Object return_val = Registry.RegisterProfession(rt.Name(), modid, poi.getId(), immutableItems, immutableBlocks, ((SoundEvent)rg.getArgs().get("event")));
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** TRADES **/
        LoadingResolver(packageName, modid, RegisterPOI.class, RegistryVillagerTrade.class, (rg, rt) -> {
            Registry.RegisterVillagerTrade(rt.Name(), modid,
                    ((RegistryProfession)rg.getArgs().get("profession")).getRawData(),
                    ((int)rg.getArgs().get("level")),
                    ((TradeOffer[])rg.getArgs().get("offers")));
            return null;
        });

        /** BIOME FEAT **/
        LoadingResolver(packageName, modid, RegisterConFeat.class, RegistryConFeat.class, (rg, rt) -> {
            if(rt.Type() == ConFeatType.OreGen) {
                Object return_val = Registry.RegisterOreConfiguredFeature(rt.Name(), modid, rg.getSupplier(),  (int)rg.getArgs().get("size"));
                rg.setRegistryData(return_val);
                return return_val;
            }
            return null;
        });

        /** PLACED FEAT **/
        LoadingResolver(packageName, modid, RegisterPlacedFeat.class, RegistryPlacedFeat.class, (rg, rt) -> {
            Object return_val = Registry.RegisterPlacedFeature(rt.Name(), modid, ((RegistryConFeat)rg.getArgs().get("conf_feat")).getRawData(),
                    ((PlacementModifierCreationHelper)rg.getArgs().get("pl_helper")).build());
            rg.setRegistryData(return_val);
            return return_val;
        });

        /** BIOME MOD **/
        LoadingResolver(packageName, modid, RegisterBiomeModification.class, RegistryBiomeModification.class, (rg, rt) -> {
             Registry.AddBiomeModification(rt.Name(), TestMod.MODID, (VinillaBiomeTypes)rg.getArgs().get("biome_type"),
                    (GenerationStep.Feature)rg.getArgs().get("gen_step"),
                    ((RegistryPlacedFeat)rg.getArgs().get("placed_feat")).getRawData()
            );
            return null;
        });

        /** CLASS INIT FUNCTIONS **/
        callInitsInPackage(packageName, modid);

        /** CREATIVE MODE TABS **/
        LoadingResolver(packageName, modid, RegisterCMT.class, RegistryCMT.class, (rg, rt) -> {
            Object return_val = Registry.RegisterCreativeModeTab(rt.Name(), modid, (ItemGroup)rg.getRawData());
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

    private static <T extends Annotation, B extends AutoRegisteryObject> void LoadingResolver(String packageName, String modId, Class<T> annotation, Class<B> registry, IAutoLoadResolver<B, T> Resolver) {
        Set<Field> annotatedFields = getFieldsAnnotatedIn(annotation, packageName, modId);
        annotatedFields.forEach(field -> {
            T anno = field.getAnnotation(annotation);
            try {
                field.setAccessible(true);
                if(field.getType().equals(registry)) {
                    B rg = ((B)field.get(null));
                    if(rg.getModid().equals(modId))
                        Resolver.accept(rg, anno);
                }
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
