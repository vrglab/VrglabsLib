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
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;
import org.vrglab.vrglabsLib.api.functionProviders.IScreenHandlerTypeCreationFunction;
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
    private static class UnregisteredData{
        public UnregisteredData(UUID registry_type, Object... args) {
            this.registry_type = registry_type;
            this.args = new ArrayList<>();
            for (Object argdata: args) {
                this.args.add(argdata);
            }
        }

        public List<Object> args;
        public UUID registry_type;
        public boolean resolved;

        public Object Obj = null;
    }

    private static Map<String, Map<UUID, ICallBack>> open_registeries = new HashMap<>();
    private static Map<String, Set<UnregisteredData>> ready_to_load_registeries = new HashMap<>();

    /**
     * Initializes a Modloader's Registry to be used for loading of objects
     * <div><i>(Using this function is extremely not recommended, unless you trully know what you are really doing, instead use {@link #initRegistry(ICallBack, IRegistryType, String)})</i></div>
     * @param _registery The registry code
     * @param _currentRegistryTypes the type of registry
     * @param modid the Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static void initRegistry(ICallBack _registery, UUID _currentRegistryTypes, String modid){
        if(open_registeries.containsKey(modid)){
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        } else{
            open_registeries.put(modid, new HashMap());
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        }
        if(ready_to_load_registeries.containsKey(modid) && ready_to_load_registeries.get(modid).size() > 0) {
            Constants.LOG.warn("Registry " +_currentRegistryTypes + " for " +  modid + " has unresolved cached object's for registering, Registering objects now ");
            for (UnregisteredData data: ready_to_load_registeries.get(modid)) {
                if(!data.resolved && data.registry_type == _currentRegistryTypes){
                    Constants.LOG.info("Registring " + _currentRegistryTypes + "  " +  data.args.toArray()[0] + " for " + modid);
                    data.Obj = _registery.accept(data.args.toArray());
                    data.resolved = true;
                }
            }
        }
    }

    /**
     * Initializes a Modloader's Registry to be used for loading of objects
     * @param _registery The registry code
     * @param _currentRegistryTypes the type of registry
     * @param modid the Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static void initRegistry(ICallBack _registery, IRegistryType _currentRegistryTypes, String modid){
        initRegistry(_registery, _currentRegistryTypes.getTypeId(), modid);
    }

    /**
     * Creates an Event resolver for forge
     * @param eventData The event data
     * @param resolver The resolver code
     * @param ResolveTypeOf The type of Object this event resolves
     * @param modid The  Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static void ForgeEventResolver(Object eventData, ICallBack resolver, IRegistryType ResolveTypeOf, String modid){
        ForgeEventResolver(eventData, resolver, ResolveTypeOf.getTypeId(), modid);
    }

    /**
     * Creates an Event resolver for forge
     * <div><i>(Using this function is extremely not recommended, unless you truly know what you are really doing, instead use {@link #ForgeEventResolver(Object, ICallBack, IRegistryType, String)})</i></div>
     * @param eventData The event data
     * @param resolver The resolver code
     * @param ResolveTypeOf The type of Object this event resolves
     * @param modid The  Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static void ForgeEventResolver(Object eventData, ICallBack resolver, UUID ResolveTypeOf, String modid){
        if(ready_to_load_registeries.containsKey(modid) && ready_to_load_registeries.get(modid).size() > 0) {
            for (UnregisteredData data: ready_to_load_registeries.get(modid)) {
                if(!data.resolved && data.registry_type == ResolveTypeOf){
                    data.Obj = resolver.accept(data.args.toArray(), eventData);
                }
            }
        }
    }

    /**
     * Register's a new Item for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Item name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Item Instance
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterItem(String name, String Modid, IClampedCallBack<Item> aNew, Supplier<Item.Properties> settings) {
        Object data = SimpleRegister(RegistryTypes.ITEM, Modid, name, aNew, settings);
        //DataGenRegistry.RegisterItem(Modid, data);
        return data;
    }

    /**
     * Register's a new Block for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Block name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Block Instance
     * @param settings The {@link net.minecraft.world.item.Item.Properties} to use for the Blocks inventory Item
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterBlock(String name, String Modid, IClampedCallBack<Block> aNew, Supplier<Item.Properties> settings, Supplier<BlockBehaviour.Properties> blockSettings) {
        Object data = SimpleRegister(RegistryTypes.BLOCK, Modid, name, aNew, settings, blockSettings);
        //DataGenRegistry.RegisterBlock(Modid, data);
        return data;
    }

    /**
     * Register's a new Block Entity for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Block Entity name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The ::new which gives us the Block Entity Instance
     * @param block The block to attach the entity to (NOT converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterBlockEntityType(String name, String Modid, IBlockEntityLoaderFunction aNew, Object block) {
        return SimpleRegister(RegistryTypes.BLOCK_ENTITY_TYPE, Modid, name, aNew, block);
    }

    /**
     * Register's a new Screen Handler for MC.
     * @param name The Screen Handler name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The ::new which gives us the Screen Handler Instance
     * @return The registered data (The return value of this Function CAN NOT be converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterScreenHandlerType(String name, String Modid, IScreenHandlerTypeCreationFunction aNew) {
        return SimpleRegister(RegistryTypes.SCREEN_HANDLER_TYPE, Modid, name, aNew);
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
     * @param Modid The Mod Id of the Registerar
     * @param aNew The supplier which gives us the Block Instance
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterItemlessBlock(String name, String Modid, IClampedCallBack<Block> aNew, Supplier<BlockBehaviour.Properties> settingsSupplier) {
        return SimpleRegister(RegistryTypes.ITEMLESS_BLOCK, Modid, name, aNew, settingsSupplier);
    }

    /**
     * Register's a new POI (Point of  Interest) for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The POI name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param block The block to attach the entity to (NOT converted using {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)})
     * @param tickcount The POI tick count
     * @param searchdistance The Search distance for the POI
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterPOI(String name, String Modid, Object block, int tickcount, int searchdistance) {
        return SimpleRegister(RegistryTypes.POI, Modid, name, tickcount, searchdistance, block);
    }

    /**
     * Register's a new Villager Profession for MC. Based on what Mod loader we are on, We either receive the Object itself or a
     * RegistryObject of the Object, to safely use the Object in other pieces of
     * code use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)}
     * @param name The Villager Profession name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param aNew The POI name (aka ID)
     * @param itemImmutableSet Gatherable Items
     * @param blockImmutableSet Secondary Job Site's
     * @param sound Work Sound
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object RegisterProfession(String name, String Modid, String aNew, Item[] itemImmutableSet, Block[] blockImmutableSet, SoundEvent sound) {
        return SimpleRegister(RegistryTypes.PROFESSION, Modid, name, aNew, itemImmutableSet, blockImmutableSet, sound);
    }

    public static void RegisterVillagerTrade(String name, String Modid, Object profession, int level, TradeWithVillager... trades) {
        SimpleRegister(RegistryTypes.TRADE, Modid, name, profession, level, trades);
    }

    public static Object RegisterOreConfiguredFeature(String name, String Modid, Supplier<List<OreFeature>> targets, int size) {
        return SimpleRegister(RegistryTypes.CONFIGURED_FEAT_ORE,  Modid, name, Feature.ORE, targets, size);
    }

    public static Object RegisterPlacedFeature(String name, String Modid, Object configured_feat, Object data) {
        return SimpleRegister(RegistryTypes.PLACED_FEAT, Modid, name, configured_feat, data);
    }

    public static void AddBiomeModification(String name, String Modid, Biomes biomeTypes, GenerationStep.Carving gen_step, Object Placed_ore) {
        SimpleRegister(RegistryTypes.BIOME_MODIFICATIONS,  Modid, name, biomeTypes, gen_step, Placed_ore);
    }

    /**
     *  Register's a new Recipe Serializer
     *
     * @param name The Villager Profession name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param serializer_instance The instance to the Recipe Serializer class
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static Object RegisterRecipeSerializer(String name, String Modid, RecipeSerializer serializer_instance) {
        return SimpleRegister(RegistryTypes.RECIPE_SERIALIZER, Modid, name, serializer_instance);
    }

    /**
     *  Register's a new Recipe Type
     *
     * @param name The Villager Profession name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param type_instance The instance to the Recipe Type class
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     */
    public static Object RegisterRecipeType(String name, String Modid, RecipeType type_instance) {
        return SimpleRegister(RegistryTypes.RECIPE_TYPE, Modid, name, type_instance);
    }


    /**
     *  Register's a new Creative mode tab
     *
     * @param name The tab name (aka ID)
     * @param Modid The Mod Id of the Registerar
     * @param tab The instance to the tab
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0-mc1.20.4
     */
    public static <T extends CreativeModeTab> Object RegisterCreativeModeTab(String name, String Modid, T tab) {
       return SimpleRegister(RegistryTypes.CREATIVE_MODE_TAB,  Modid, name, tab);
    }

    /**
     * Sends data to the modloader for something to be registered for MC
     * @param type The type to use for registeration
     * @param Modid The Mod Id of the Registerar
     * @param args All the arguments needed to register the Object (On the callback end of this interaction the arguments are fed in the EXACT same order)
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static Object SimpleRegister(IRegistryType type, String Modid, Object... args){
       return SimpleRegister(type.getTypeId(), Modid, args);
    }


    /**
     * Sends data to the modloader for something to be registered for MC
     * <div><i>(Using this function is extremely not recommended, unless you trully know what you are really doing, instead use {@link #SimpleRegister(IRegistryType, String, Object...)})</i></div>
     * @param type The type to use for registeration
     * @param Modid The Mod Id of the Registerar
     * @param args All the arguments needed to register the Object (On the callback end of this interaction the arguments are fed in the EXACT same order)
     * @return The registered data
     *
     * @author Arad Bozorgmehr
     * @since 1.1.0
     * @from 1.19.4
     */
    public static Object SimpleRegister(UUID type, String Modid, Object... args){
        if(open_registeries.containsKey(Modid) && open_registeries.get(Modid).containsKey(type)) {
            Constants.LOG.info("Registry of type" + type + " is registering " +  args[0] + " for " + Modid);
            return open_registeries.get(Modid).get(type).accept(args);
        }
        else {
            Constants.LOG.error("Registry "+ type + " is not yet initialized caching object " +  args[0] + " in mod " + Modid + " for later registration");
            UnregisteredData data = new UnregisteredData(type, args);
            if(!ready_to_load_registeries.containsKey(Modid)) {
                ready_to_load_registeries.put(Modid, new HashSet<>());
                ready_to_load_registeries.get(Modid).add(data);
            }
            else
                ready_to_load_registeries.get(Modid).add(data);
            return data.Obj;
        }
    }
}
