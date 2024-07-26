package org.Vrglab.neoforge.Utils;

import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.level.biome.BiomeModifications;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.*;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.event.listener.GameEventListener;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.poi.PointOfInterestType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.moddiscovery.MinecraftLocator;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.Vrglab.AutoRegisteration.AutoRegistryLoader;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Registration.Bootstrapper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IScreenHandledCreationFunction;
import org.Vrglab.Modloader.enumTypes.BootstrapType;
import org.Vrglab.Modloader.enumTypes.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;
import org.Vrglab.Reflections.Reflections;
import org.Vrglab.Reflections.scanners.Scanners;
import org.Vrglab.Reflections.util.ConfigurationBuilder;
import org.Vrglab.Reflections.util.FilterBuilder;
import org.Vrglab.Utils.VLModInfo;
import org.objectweb.asm.Type;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class NeoForgeRegistryCreator {


   public static ICallBack TradeRegistryEventCallback = new ICallBack() {
        @Override
        public Object accept(Object... args) {
            Object[] arg = (Object[]) args[0];
            if(((VillagerTradesEvent)args[1]).getType() == ((DeferredHolder<VillagerProfession, ?>)arg[1]).get()) {
                Int2ObjectMap<List<TradeOffers.Factory>> trades = ((VillagerTradesEvent)args[1]).getTrades();
                for (TradeOffer data: (TradeOffer[])arg[3]) {
                    trades.get((int)arg[2]).add((trader, rand) -> data);
                }
            }
            return null;
        }
   };




    public static void Create(IEventBus eventBus, String modid) {
        createAutoRegistry(modid);


        DeferredRegister<ItemGroup> ITEM_GROUP_REGISTRY = DeferredRegister.create(Registries.ITEM_GROUP.getKey(), modid);
        ITEM_GROUP_REGISTRY.register(eventBus);

        DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(modid);
        ITEM_REGISTRY.register(eventBus);

        DeferredRegister.Blocks BLOCK_REGISTRY = DeferredRegister.createBlocks(modid);
        BLOCK_REGISTRY.register(eventBus);

        DeferredRegister<PointOfInterestType> POI_REGISTRY = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE.getKey(), modid);
        POI_REGISTRY.register(eventBus);

        DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(Registries.VILLAGER_PROFESSION.getKey(), modid);
        PROFESSION_REGISTRY.register(eventBus);

        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER.getKey(), modid);
        RECIPE_SERIALIZER_REGISTRY.register(eventBus);

        DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE.getKey(), modid);
        RECIPE_TYPE_REGISTRY.register(eventBus);

        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((DeferredHolder)args[0]).get();
            }
        };

        OreGenFeatCreationHelper.ObjectBlockToStateConverted = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((Block)((DeferredBlock)args[0]).get()).getDefaultState();
            }
        };

        PlacementModifierCreationHelper.getHeightModifications = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom((Integer) args[0]), YOffset.aboveBottom((Integer) args[1]));
            }
        };

        ICallBack Itemcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ITEM_REGISTRY.register(args[0].toString(), (Supplier<? extends Item>) args[1]);
            }
        };

        ICallBack Blockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                DeferredHolder<Block, ?> b = BLOCK_REGISTRY.register(args[0].toString(), (Supplier<? extends Block>) args[1]);
               ITEM_REGISTRY.register(args[0].toString(), ()->new BlockItem(b.get(), ((Supplier<Item.Settings>) args[2]).get()));
               return b;
            }
        };

        ICallBack ItemlessBlockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return BLOCK_REGISTRY.register(args[0].toString(), (Supplier<? extends Block>) args[1]);
            }
        };


        ICallBack POIcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return POI_REGISTRY.register(args[0].toString(), ()-> new PointOfInterestType(ImmutableSet.copyOf(((DeferredHolder<Block, ?>)args[3]).get().getStateManager().getStates()), 1,1));
            }
        };


        ICallBack Professioncallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey<PointOfInterestType> poi =  RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(modid, args[1].toString()));
                return PROFESSION_REGISTRY.register(args[0].toString(), ()->new VillagerProfession(modid+"."+args[0].toString(), entry->entry.matchesKey(poi), entry->entry.matchesKey(poi), args[2] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Item[])args[2])), args[3] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Block[])args[3])), args[4] == null ? null : (SoundEvent)args[4]));
            }
        };

        ICallBack OreGenRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey r = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.CONFIGUERED_FEAT_ORES, modid, r, args[1], ((Supplier<List<OreFeatureConfig.Target>>) args[2]),  (int)args[3]);
                return r;
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey r = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.PLACED_FEAT, modid, r, args[1], args[2]);
                return r;
            }
        };

        ICallBack RecipeSerializerRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return RECIPE_SERIALIZER_REGISTRY.register(args[0].toString(), ()->(RecipeSerializer)args[1]);
            }
        };

        ICallBack RecipeTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return RECIPE_TYPE_REGISTRY.register(args[0].toString(), ()->(RecipeType)args[1]);
            }
        };

        ICallBack CreativeModeTabcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ITEM_GROUP_REGISTRY.register(args[0].toString(), ()->(ItemGroup)args[1]);
            }
        };

        Registry.initRegistry(CreativeModeTabcallback, RegistryTypes.CREATIVE_MODE_TAB, modid);
        Registry.initRegistry(Itemcallback, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockcallback, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(Blockcallback, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIcallback, RegistryTypes.POI, modid);
        Registry.initRegistry(Professioncallback, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(OreGenRegistryCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        Registry.initRegistry(RecipeSerializerRegistryCallBack, RegistryTypes.RECIPE_SERIALIZER, modid);
        Registry.initRegistry(RecipeTypeRegistryCallBack, RegistryTypes.RECIPE_TYPE, modid);
    }

    public static void villagerTradeEventResolver(VillagerTradesEvent e, String modid) {
        Registry.ForgeEventResolver(e, TradeRegistryEventCallback, RegistryTypes.TRADE, modid);
    }

    public static void CreateClient(String modid){
        ICallBack HandledScreensRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {

                HandledScreens.Provider provider = new HandledScreens.Provider() {
                    @Override
                    public Screen create(ScreenHandler handler, PlayerInventory playerInventory, Text title) {
                        return ((IScreenHandledCreationFunction)args[2]).create((org.Vrglab.Screen.ScreenHandler) handler, playerInventory, title);
                    }
                };
                HandledScreens.register((ScreenHandlerType)args[1], provider);
                return null;
            }
        };
        Registry.initRegistry(HandledScreensRegistryCallBack, RegistryTypes.HANDLED_SCREEN, modid);
    }


    public static void boostrap(RegistryBuilder builder, String modid) {
        builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
            return r.register((RegistryKey<ConfiguredFeature<?,?>>) args[0], new ConfiguredFeature((Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(),  (int)args[3])));
        }, BootstrapType.CONFIGUERED_FEAT_ORES, modid));


        builder.addRegistry(RegistryKeys.PLACED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
            var config_feat_lookup = r.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            return r.register((RegistryKey<PlacedFeature>)args[0], new PlacedFeature(config_feat_lookup.getOrThrow((RegistryKey<ConfiguredFeature<?, ?>>) args[1]), (List<PlacementModifier>)args[2]));
        }, BootstrapType.PLACED_FEAT, modid));
    }

    private static void createAutoRegistry(String modid){
        AutoRegistryLoader.collectAnnotatedFieldsForMod = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Reflections reflections = new Reflections(
                        new ConfigurationBuilder()
                                .forPackage(args[0].toString())
                                .filterInputsBy(new FilterBuilder().includePackage(args[0].toString()))
                                .setScanners(Scanners.FieldsAnnotated));
                return reflections.getFieldsAnnotatedWith((Class<? extends Annotation>)args[1]);
            }
        };

        AutoRegistryLoader.collectAnnotatedTypesForMod = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Reflections reflections = new Reflections(
                        new ConfigurationBuilder()
                                .forPackage(args[0].toString())
                                .filterInputsBy(new FilterBuilder().includePackage(args[0].toString()))
                                .setScanners(Scanners.TypesAnnotated));
                return reflections.getTypesAnnotatedWith((Class<? extends Annotation>)args[1]);
            }
        };
    }
}
