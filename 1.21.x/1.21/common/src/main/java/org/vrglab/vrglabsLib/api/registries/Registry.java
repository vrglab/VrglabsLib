package org.vrglab.vrglabsLib.api.registries;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.ai.behavior.TradeWithVillager;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.azure.azurelib.world.Item.AzureItem;
import org.vrglab.vrglabsLib.Utils.ReflectionUtil;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedSingleCallback;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;
import org.vrglab.vrglabsLib.api.functionProviders.IScreenHandlerTypeCreationFunction;
import org.vrglab.vrglabsLib.api.registries.interfaces.BootstrapType;
import org.vrglab.vrglabsLib.api.registries.interfaces.IRegistryType;
import org.vrglab.vrglabsLib.api.registries.interfaces.RegistryTypes;
import org.vrglab.vrglabsLib.core.Constants;

import java.util.*;
import java.util.function.Supplier;

/**
 * Class to handle Data registration Across Modloaders and various MC version
 *
 * @author Arad Bozorgmehr
 * @since 1.0.0
 */
public class Registry {
    private static class UnregisteredData {
        UnregisteredData(UUID registryType, Object... args) {
            this.registryType = registryType;
            this.args = new ArrayList<>();
            for (Object argdata: args) {
                this.args.add(argdata);
            }
        }

        public List<Object> args;
        public UUID registryType;
        public boolean resolved;

        public Object obj = null;
    }

    private static final Map<String, Map<UUID, ICallBack>> OPEN_REGISTRIES = new HashMap<>();
    private static final Map<String, Set<UnregisteredData>> READY_TO_LOAD_REGISTRIES = new HashMap<>();

    /**
     * Initializes a Modloader's Registry to be used for loading of objects
     * <div><i>(Using this function is extremely not recommended, unless you trully know what you are really doing, instead use {@link #initRegistry(ICallBack, IRegistryType, String)})</i></div>
     * @param registry The registry code
     * @param currentRegistryTypes the type of registry
     * @param modid the Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static void initRegistry(ICallBack registry, UUID currentRegistryTypes, String modid) {
        if (OPEN_REGISTRIES.containsKey(modid)) {
            OPEN_REGISTRIES.get(modid).put(currentRegistryTypes, registry);
        } else {
            OPEN_REGISTRIES.put(modid, new HashMap());
            OPEN_REGISTRIES.get(modid).put(currentRegistryTypes, registry);
        }
        if (READY_TO_LOAD_REGISTRIES.containsKey(modid) && READY_TO_LOAD_REGISTRIES.get(modid).size() > 0) {
            Constants.LOG.warn("Registry " +currentRegistryTypes + " for " +  modid + " has unresolved cached object's for registering, Registering objects now ");
            for (UnregisteredData data: READY_TO_LOAD_REGISTRIES.get(modid)) {
                if (!data.resolved && data.registryType == currentRegistryTypes) {
                    Constants.LOG.info("Registring " + currentRegistryTypes + "  " +  data.args.toArray()[0] + " for " + modid);
                    data.obj = registry.accept(data.args.toArray());
                    data.resolved = true;
                }
            }
        }
    }

    /**
     * Initializes a Modloader's Registry to be used for loading of objects
     * @param registry The registry code
     * @param currentRegistryTypes the type of registry
     * @param modid the Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static void initRegistry(ICallBack registry, IRegistryType currentRegistryTypes, String modid){
        initRegistry(registry, currentRegistryTypes.getTypeId(), modid);
    }

    /**
     * Creates an Event resolver for forge
     * @param eventData The event data
     * @param resolver The resolver code
     * @param resolveTypeOf The type of Object this event resolves
     * @param modid The  Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static void ForgeEventResolver(Object eventData, ICallBack resolver, IRegistryType resolveTypeOf, String modid){
        ForgeEventResolver(eventData, resolver, resolveTypeOf.getTypeId(), modid);
    }

    /**
     * Creates an Event resolver for forge
     * <div><i>(Using this function is extremely not recommended, unless you truly know what you are really doing, instead use {@link #ForgeEventResolver(Object, ICallBack, IRegistryType, String)})</i></div>
     * @param eventData The event data
     * @param resolver The resolver code
     * @param resolveTypeOf The type of Object this event resolves
     * @param modid The Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static void ForgeEventResolver(Object eventData, ICallBack resolver, UUID resolveTypeOf, String modid){
        if (READY_TO_LOAD_REGISTRIES.containsKey(modid) && READY_TO_LOAD_REGISTRIES.get(modid).size() > 0) {
            for (UnregisteredData data: READY_TO_LOAD_REGISTRIES.get(modid)) {
                if (!data.resolved && data.registryType == resolveTypeOf) {
                    data.obj = resolver.accept(data.args.toArray(), eventData);
                }
            }
        }
    }

    /**
     * Register's a new Item for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Item name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Item Instance
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static <T extends Item> Object RegisterItem(String name, String modId, IClampedCallBack<T> aNew, Supplier<Item.Properties> settings) {
        Object data = SimpleRegister(RegistryTypes.ITEM, modId, name, aNew, settings);
        DataGenRegistry.RegisterItem(modId, data);
        return data;
    }

    public static <T extends Item> Object RegisterItem(String name, String modId, IClampedCallBack<T> aNew, Supplier<Item.Properties> settings, Class<T> clazz) {

        Object data = SimpleRegister(RegistryTypes.ITEM, modId, name, aNew, settings);

        if (ReflectionUtil.isSubclassOrSame(clazz, AzureArmor.class)) {
            Bootstrapper.SimpleRegister(BootstrapType.AZURE_ARMOR.getTypeId(), modId, data, clazz);
        }

        if (ReflectionUtil.isSubclassOrSame(clazz, AzureItem.class)) {
            Bootstrapper.SimpleRegister(BootstrapType.AZURE_ITEM.getTypeId(), modId, data, clazz);
        }

        if (ReflectionUtil.isSubclassOrSame(clazz, AzureItem.class) || ReflectionUtil.isSubclassOrSame(clazz, AzureArmor.class)) {
            Bootstrapper.SimpleRegister(BootstrapType.AZURE_ID.getTypeId(), modId, data, clazz);
        }
        DataGenRegistry.RegisterItem(modId, data);
        return data;
    }

    /**
     * Register's a new Block for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Block name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Block Instance
     * @param settings The {@link net.minecraft.world.item.Item.Properties} to use for the Blocks inventory Item
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterBlock(String name, String modId, IClampedSingleCallback<Block, BlockBehaviour.Properties> aNew, Supplier<Item.Properties> settings, Supplier<BlockBehaviour.Properties> blockSettings) {
        Object data = SimpleRegister(RegistryTypes.BLOCK, modId, name, aNew, settings, blockSettings);
        DataGenRegistry.RegisterBlock(modId, data);
        return data;
    }

    /**
     * Register's a new Block Entity for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Block Entity name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The ::new which gives us the Block Entity Instance
     * @param block The block to attach the entity to (NOT converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterBlockEntityType(String name, String modId, IBlockEntityLoaderFunction aNew, Object block) {
        return SimpleRegister(RegistryTypes.BLOCK_ENTITY_TYPE, modId, name, aNew, block);
    }

    /**
     * Register's a new Screen Handler for MC.
     * @param name The Screen Handler name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The ::new which gives us the Screen Handler Instance
     * @return The registered data (The return value of this Function CAN NOT be converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterScreenHandlerType(String name, String modId, IScreenHandlerTypeCreationFunction aNew) {
        return SimpleRegister(RegistryTypes.SCREEN_HANDLER_TYPE, modId, name, aNew);
    }

    /**
     * Register's a new Handled Screen for MC.
     * <div>You can call this function within any init() functions</div>
     * @param name The name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The ::new which gives us the Handled Screen Instance
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    /*public static  <T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<T>>  void RegisterHandledScreen(String name, String Modid, Object handlerType, IScreenHandledCreationFunction<T, U> aNew) {
        SimpleRegister(RegistryTypes.HANDLED_SCREEN, Modid, name, handlerType, aNew);
    }*/

    /**
     * Register's a new Itemless Block for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Block name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Block Instance
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterItemlessBlock(String name, String modId, IClampedSingleCallback<Block, BlockBehaviour.Properties> aNew, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return SimpleRegister(RegistryTypes.ITEMLESS_BLOCK, modId, name, aNew, settingsSupplier);
    }

    /**
     * Register's a new POI (Point of  Interest) for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The POI name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param block The block to attach the entity to (NOT converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     * @param tickcount The POI tick count
     * @param searchdistance The Search distance for the POI
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterPOI(String name, String modId, Object block, int tickcount, int searchdistance) {
        return SimpleRegister(RegistryTypes.POI, modId, name, tickcount, searchdistance, block);
    }

    /**
     * Register's a new Villager Profession for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Villager Profession name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param aNew The POI name (aka ID)
     * @param itemImmutableSet Gatherable Items
     * @param blockImmutableSet Secondary Job Site's
     * @param sound Work Sound
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterProfession(String name, String modId, String aNew, Item[] itemImmutableSet, Block[] blockImmutableSet, SoundEvent sound) {
        return SimpleRegister(RegistryTypes.PROFESSION, modId, name, aNew, itemImmutableSet, blockImmutableSet, sound);
    }

    public static void RegisterVillagerTrade(String name, String modId, Object profession, int level, TradeWithVillager... trades) {
        SimpleRegister(RegistryTypes.TRADE, modId, name, profession, level, trades);
    }

    public static Object RegisterOreConfiguredFeature(String name, String modId, Supplier<List<OreFeature>> targets, int size) {
        return SimpleRegister(RegistryTypes.CONFIGURED_FEAT_ORE,  modId, name, Feature.ORE, targets, size);
    }

    public static Object RegisterPlacedFeature(String name, String modId, Object configuredFeat, Object data) {
        return SimpleRegister(RegistryTypes.PLACED_FEAT, modId, name, configuredFeat, data);
    }

    public static void AddBiomeModification(String name, String modId, Biomes biomeTypes, GenerationStep.Carving genStep, Object placedOre) {
        SimpleRegister(RegistryTypes.BIOME_MODIFICATIONS,  modId, name, biomeTypes, genStep, placedOre);
    }

    /**
     *  Register's a new Recipe Serializer
     *
     * @param name The Villager Profession name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param serializerInstance The instance to the Recipe Serializer class
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static Object RegisterRecipeSerializer(String name, String modId, RecipeSerializer serializerInstance) {
        return SimpleRegister(RegistryTypes.RECIPE_SERIALIZER, modId, name, serializerInstance);
    }

    /**
     *  Register's a new Recipe Type
     *
     * @param name The Villager Profession name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param typeInstance The instance to the Recipe Type class
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static Object RegisterRecipeType(String name, String modId, RecipeType typeInstance) {
        return SimpleRegister(RegistryTypes.RECIPE_TYPE, modId, name, typeInstance);
    }


    /**
     *  Register's a new Creative mode tab
     *
     * @param name The tab name (aka ID)
     * @param modId The Mod Id of the Registerar
     * @param tab The instance to the tab
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0-mc1.20.4
     */
    public static Object RegisterCreativeModeTab(String name, String modId, Supplier<CreativeModeTab> tab) {
       return SimpleRegister(RegistryTypes.CREATIVE_MODE_TAB,  modId, name, tab);
    }

    /**
     * Sends data to the modloader for something to be registered for MC
     * @param type The type to use for registeration
     * @param modId The Mod Id of the Registerar
     * @param args All the arguments needed to register the Object (On the callback end of this interaction the arguments are fed in the EXACT same order)
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object SimpleRegister(IRegistryType type, String modId, Object... args){
       return SimpleRegister(type.getTypeId(), modId, args);
    }


    /**
     * Sends data to the modloader for something to be registered for MC
     * <div><i>(Using this function is extremely not recommended, unless you trully know what you are really doing, instead use {@link #SimpleRegister(IRegistryType, String, Object...)})</i></div>
     * @param type The type to use for registeration
     * @param modId The Mod Id of the Registerar
     * @param args All the arguments needed to register the Object (On the callback end of this interaction the arguments are fed in the EXACT same order)
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static Object SimpleRegister(UUID type, String modId, Object... args){
        if (OPEN_REGISTRIES.containsKey(modId) && OPEN_REGISTRIES.get(modId).containsKey(type)) {
            Constants.LOG.info("Registry of type" + type + " is registering " +  args[0] + " for " + modId);
            return OPEN_REGISTRIES.get(modId).get(type).accept(args);
        } else {
            Constants.LOG.error("Registry "+ type + " is not yet initialized caching object " +  args[0] + " in mod " + modId + " for later registration");
            UnregisteredData data = new UnregisteredData(type, args);
            if (!READY_TO_LOAD_REGISTRIES.containsKey(modId)) {
                READY_TO_LOAD_REGISTRIES.put(modId, new HashSet<>());
                READY_TO_LOAD_REGISTRIES.get(modId).add(data);
            } else {
                READY_TO_LOAD_REGISTRIES.get(modId).add(data);
            }
            return data.obj;
        }
    }
}
