package org.vrglab.vrglabsLib.Forge.Utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.vrglab.reflections.Reflections;
import org.vrglab.reflections.scanners.Scanners;
import org.vrglab.reflections.util.ConfigurationBuilder;
import org.vrglab.reflections.util.FilterBuilder;
import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.api.autoRegistry.World.BlockEntity;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.api.helpers.OreGenFeatCreationHelper;
import org.vrglab.vrglabsLib.api.helpers.PlacementModifierCreationHelper;
import org.vrglab.vrglabsLib.api.helpers.TypeTransformer;
import org.vrglab.vrglabsLib.api.registries.Registry;
import org.vrglab.vrglabsLib.api.registries.interfaces.RegistryTypes;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;
import org.vrglab.vrglabsLib.Utils.Utils;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public class VrglabsForgeInitializer {

    public static void Initialize(IEventBus eventBus, String modid, String modPackage) {
        Create(eventBus, modid);
        VrglabsInitializer.Initialize(modid, modPackage, eventBus);
    }



    public static ICallBack TradeRegistryEventCallback = new ICallBack() {
        @Override
        public Object accept(Object... args) {
            Object[] arg = (Object[]) args[0];
            if(((VillagerTradesEvent)args[1]).getType() == ((RegistryObject<VillagerProfession>)arg[1]).get()) {
               /* Int2ObjectMap<List<TradeOffers.Factory>> trades = ((VillagerTradesEvent)args[1]).getTrades();
                for (TradeOffer data: (TradeOffer[])arg[3]) {
                    trades.get((int)arg[2]).add((trader, rand) -> data);
                }*/
            }
            return null;
        }
    };




    public static void Create(IEventBus eventBus, String modid) {
        createAutoRegistry(modid);



        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((RegistryObject)args[0]).get();
            }
        };
        createEnergyCallBacks();
        createOreGenStatics();
        createNetworkStatics();

        DeferredRegister<CreativeModeTab> ITEM_GROUP_REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, modid);
        ITEM_GROUP_REGISTRY.register(eventBus);

        DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(Registries.ITEM,modid);
        ITEM_REGISTRY.register(eventBus);

        DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(Registries.BLOCK,modid);
        BLOCK_REGISTRY.register(eventBus);

        DeferredRegister<PoiType> POI_REGISTRY = DeferredRegister.create(Registries.POINT_OF_INTEREST_TYPE, modid);
        POI_REGISTRY.register(eventBus);

        DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(Registries.VILLAGER_PROFESSION, modid);
        PROFESSION_REGISTRY.register(eventBus);

        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, modid);
        BLOCK_ENTITY_TYPE.register(eventBus);

        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTRY = DeferredRegister.create(Registries.RECIPE_SERIALIZER, modid);
        RECIPE_SERIALIZER_REGISTRY.register(eventBus);

        DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTRY = DeferredRegister.create(Registries.RECIPE_TYPE, modid);
        RECIPE_TYPE_REGISTRY.register(eventBus);

        ICallBack Itemcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {

                Supplier< ? extends Item> supplier = new Supplier<Item>() {

                    /**
                     * Gets a result.
                     *
                     * @return a result
                     */
                    @Override
                    public Item get() {
                        Item.Properties properties = Utils.MakeSafeSettings(((Supplier<Item.Properties>)args[2]).get(), RegistryTypes.ITEM, ResourceLocation.parse(args[0].toString()));
                        return ((IClampedCallBack<Item>)args[1]).accept(properties);
                    }
                };

                return ITEM_REGISTRY.register(args[0].toString(), supplier);
            }
        };

        ICallBack Blockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryObject<Block> b = BLOCK_REGISTRY.register(args[0].toString(), (Supplier<? extends Block>) args[1]);
                ITEM_REGISTRY.register(args[0].toString(), ()->new BlockItem(b.get(), ((Supplier<Item.Properties>) args[2]).get()));
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
                return POI_REGISTRY.register(args[0].toString(), ()-> new PoiType(ImmutableSet.copyOf(((RegistryObject<Block>)args[3]).get().getStateDefinition().getPossibleStates()), 1,1));
            }
        };


        ICallBack Professioncallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                TagKey<PoiType> poi =  TagKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(modid, args[1].toString()));
                return PROFESSION_REGISTRY.register(args[0].toString(), ()->new VillagerProfession(modid+"."+args[0].toString(), entry->entry.is(poi), entry->entry.is(poi), args[2] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Item[])args[2])), args[3] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Block[])args[3])), args[4] == null ? null : (SoundEvent) args[4]));
            }
        };

        ICallBack OreGenRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                /*TagKey r = TagKey.create(RegistryKeys.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.CONFIGUERED_FEAT_ORES, modid, r, args[1], ((Supplier<List<OreFeatureConfig.Target>>) args[2]),  (int)args[3]);
                return r;*/

                return null;
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                /*RegistryKey r = RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.PLACED_FEAT, modid, r, args[1], args[2]);
                return r;*/

                return null;
            }
        };

        ICallBack BlockEntityTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                //TODO: this implementation no longer works and needs replacement
                //return BLOCK_ENTITY_TYPE.register(args[0].toString(), ()->BlockEntityType.BlockEntityFactory.create((blockPos,blockState)->((IBlockEntityLoaderFunction)args[1]).create(blockPos, blockState), ((Block)((DeferredBlock)args[2]).get())).build(null));
                return null;
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
                return ITEM_GROUP_REGISTRY.register(args[0].toString(), ()->(CreativeModeTab) args[1]);
            }
        };

        Registry.initRegistry(CreativeModeTabcallback, RegistryTypes.CREATIVE_MODE_TAB, modid);
        Registry.initRegistry(Itemcallback, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockcallback, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(Blockcallback, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(BlockEntityTypeRegistryCallBack, RegistryTypes.BLOCK_ENTITY_TYPE, modid);
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

               /* HandledScreens.Provider provider = new HandledScreens.Provider() {
                    @Override
                    public Screen create(ScreenHandler handler, PlayerInventory playerInventory, Text title) {
                        return ((IScreenHandledCreationFunction)args[2]).create((org.Vrglab.Screen.ScreenHandler) handler, playerInventory, title);
                    }
                };*/
                //TODO: this implementation no longer works and needs replacement
                //HandledScreens.register((ScreenHandlerType)args[1], provider);
                return null;
            }
        };
        Registry.initRegistry(HandledScreensRegistryCallBack, RegistryTypes.HANDLED_SCREEN, modid);
    }


    public static void boostrap(RegistryBuilder builder, String modid) {
        /*builder.onAdd((r)-> Bootstrapper.initBootstrapper((args)->{
            return r.register((TagKey<ConfiguredFeature<?,?>>) args[0], new ConfiguredFeature((Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(),  (int)args[3])));
        }, BootstrapType.CONFIGUERED_FEAT_ORES, modid));


        builder.onAdd((r)->Bootstrapper.initBootstrapper((args)->{
            var config_feat_lookup = r.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            return r.register((RegistryKey<PlacedFeature>)args[0], new PlacedFeature(config_feat_lookup.getOrThrow((RegistryKey<ConfiguredFeature<?, ?>>) args[1]), (List<PlacementModifier>)args[2]));
        }, BootstrapType.PLACED_FEAT, modid));*/
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

        AutoRegistryLoader.entityTypeBlockSelector = new ICallBack() {

            @Override
            public Object accept(Object... args) {
                return ((org.vrglab.vrglabsLib.api.autoRegistry.World.Block)((BlockEntity)args[0]).getArgs().get("block")).getRawData();
            }
        };
    }

    private static void createEnergyCallBacks() {
       /* EnergyStorageUtils.createStorageInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return new net.neoforged.neoforge.energy.EnergyStorage(Math.toIntExact((long)args[0]), Math.toIntExact((long)args[1]), Math.toIntExact((long)args[2]), Math.toIntExact((long)args[3])) {
                    @Override
                    public int receiveEnergy(int maxReceive, boolean simulate) {
                        try {
                            ((EnergyStorage)args[4]).makeDirty.accept();
                        } catch (Throwable t) {

                        }
                        return super.receiveEnergy(maxReceive, simulate);
                    }

                    @Override
                    public int extractEnergy(int maxExtract, boolean simulate) {
                        try {
                            ((EnergyStorage)args[4]).makeDirty.accept();
                        } catch (Throwable t) {

                        }
                        return super.extractEnergy(maxExtract, simulate);
                    }
                };
            }
        };

        EnergyStorageUtils.receiveEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((net.neoforged.neoforge.energy.IEnergyStorage)args[0]).receiveEnergy(Math.toIntExact((long)args[1]), (boolean)args[2]);
            }
        };

        EnergyStorageUtils.extractEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((net.neoforged.neoforge.energy.IEnergyStorage)args[0]).extractEnergy(Math.toIntExact((long)args[1]), (boolean)args[2]);
            }
        };

        EnergyStorageUtils.hasExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return  ((((BlockEntity)args[0]) != null) && ((BlockEntity)args[0]) instanceof ICapabilityProvider)||((((BlockEntity)args[0]) != null) && ((BlockCapability)args[0]).getCapability(((BlockEntity)args[0]).getWorld(), ((BlockEntity)args[0]).getPos(), ((BlockEntity)args[0]).getCachedState(), ((BlockEntity)args[0]), Capabilities.EnergyStorage.BLOCK) != null);
            }
        };

        EnergyStorageUtils.wrapExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                net.neoforged.neoforge.energy.IEnergyStorage storage = (IEnergyStorage) ((BlockCapability)args[3]).getCapability(((BlockEntity)args[0]).getWorld(), ((BlockEntity)args[0]).getPos(), ((BlockEntity)args[0]).getCachedState(), ((BlockEntity)args[0]), Capabilities.EnergyStorage.BLOCK);
                Field maxReceiveField = null;
                try {
                    maxReceiveField = storage.getClass().getDeclaredField("maxReceive");
                } catch (NoSuchFieldException e) {

                }
                int maxReceive = 0;
                maxReceiveField.setAccessible(true);
                try {
                    maxReceive = (int) maxReceiveField.get(storage);
                } catch (IllegalAccessException e) {

                }
                Field maxExtractField = null;
                try {
                    maxExtractField = storage.getClass().getDeclaredField("maxExtract");
                } catch (NoSuchFieldException e) {

                }
                maxExtractField.setAccessible(true);
                int maxExtract = 0;
                try {
                    maxExtract = (int) maxExtractField.get(storage);
                } catch (IllegalAccessException e) {

                }
                Field capacityField = null;
                try {
                    capacityField = storage.getClass().getDeclaredField("capacity");
                } catch (NoSuchFieldException e) {

                }
                capacityField.setAccessible(true);
                int capacity = 0;
                try {
                    capacity = (int) capacityField.get(storage);
                } catch (IllegalAccessException e) {

                }
                Field energyField = null;
                try {
                    energyField = storage.getClass().getDeclaredField("energy");
                } catch (NoSuchFieldException e) {
                }
                energyField.setAccessible(true);
                int energy = 0;
                try {
                    energy = (int) energyField.get(storage);
                } catch (IllegalAccessException e) {

                }
                return new EnergyStorage(storage, capacity, maxReceive, maxExtract, energy).setBlockEntityType(((BlockEntity)args[3])).setMakeDirtyFunction(()->((BlockEntity)args[3]).markDirty());
            }
        };*/
    }

    private static void createOreGenStatics() {

        OreGenFeatCreationHelper.ObjectBlockToStateConverted = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               // return ((Block)((DeferredBlock)args[0]).get()).getDefaultState();
                return null;
            }
        };

        PlacementModifierCreationHelper.getHeightModifications = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               // return HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom((Integer) args[0]), YOffset.aboveBottom((Integer) args[1]));

                return null;
            }
        };
    }

    private static void createNetworkStatics() {
       /* Network.registerGlobalReceiver = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {

            }
        };

        Network.clientSendPacket = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {

            }
        };*/
    }
}
