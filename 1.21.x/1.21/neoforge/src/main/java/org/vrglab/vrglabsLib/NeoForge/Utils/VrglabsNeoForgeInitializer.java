package org.vrglab.vrglabsLib.NeoForge.Utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.registries.BuiltInRegistries;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.vrglab.azure.azurelib.common.animation.cache.AzIdentityRegistry;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRendererRegistry;
import org.vrglab.azure.azurelib.common.render.item.AzItemRenderer;
import org.vrglab.azure.azurelib.common.render.item.AzItemRendererRegistry;
import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.azure.azurelib.world.Item.AzureItem;
import org.vrglab.reflections.Reflections;
import org.vrglab.reflections.scanners.Scanners;
import org.vrglab.reflections.util.ConfigurationBuilder;
import org.vrglab.reflections.util.FilterBuilder;
import org.vrglab.vrglabsLib.Utils.ReflectionUtil;
import org.vrglab.vrglabsLib.api.autoRegistry.AutoRegistryLoader;
import org.vrglab.vrglabsLib.api.autoRegistry.World.BlockEntity;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.vrglab.vrglabsLib.api.helpers.OreGenFeatCreationHelper;
import org.vrglab.vrglabsLib.api.helpers.PlacementModifierCreationHelper;
import org.vrglab.vrglabsLib.api.registries.Bootstrapper;
import org.vrglab.vrglabsLib.api.registries.Registry;
import org.vrglab.vrglabsLib.api.registries.interfaces.BootstrapType;
import org.vrglab.vrglabsLib.api.registries.interfaces.RegistryTypes;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;

import java.lang.annotation.Annotation;
import java.util.function.Supplier;

public class VrglabsNeoForgeInitializer {

    public static void Initialize(IEventBus eventBus, String modid, String modPackage) {
        Create(eventBus, modid);
        VrglabsInitializer.Initialize(modid, modPackage, eventBus);
    }

    public static void InitializeCommonSetup(final FMLCommonSetupEvent event, String modid) {
        CreateCommonSetup(event, modid);
    }


    public static ICallBack TradeRegistryEventCallback = new ICallBack() {
        @SuppressWarnings("checkstyle:EmptyBlock")
        @Override
        public Object accept(Object... args) {
            Object[] arg = Utils.typeCaster(args[0]);
            if (Utils.typeCaster(args[1], VillagerTradesEvent.class).getType() == Utils.getDeferredHolderObject(arg[1], VillagerProfession.class ).get()) {
                return null;
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

        createEnergyCallBacks();
        createOreGenStatics();
        createNetworkStatics();

        DeferredRegister<CreativeModeTab> ITEM_GROUP_REGISTRY = DeferredRegister.create(BuiltInRegistries.CREATIVE_MODE_TAB, modid);
        ITEM_GROUP_REGISTRY.register(eventBus);

        DeferredRegister.Items ITEM_REGISTRY = DeferredRegister.createItems(modid);
        ITEM_REGISTRY.register(eventBus);

        DeferredRegister.Blocks BLOCK_REGISTRY = DeferredRegister.createBlocks(modid);
        BLOCK_REGISTRY.register(eventBus);

        DeferredRegister<PoiType> POI_REGISTRY = DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, modid);
        POI_REGISTRY.register(eventBus);

        DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, modid);
        PROFESSION_REGISTRY.register(eventBus);

        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, modid);
        BLOCK_ENTITY_TYPE.register(eventBus);

        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, modid);
        RECIPE_SERIALIZER_REGISTRY.register(eventBus);

        DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTRY = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, modid);
        RECIPE_TYPE_REGISTRY.register(eventBus);

        ICallBack Itemcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {

                Supplier<? extends Item> supplier = new Supplier<Item>() {

                    /**
                     * Gets a result.
                     *
                     * @return a result
                     */
                    @Override
                    public Item get() {
                        Item.Properties properties = Utils.typeCasterSupplierfied(args[2], Item.Properties.class).get();
                        return Utils.typeCasterIClampedCallBackafied(args[1], Item.class).accept(properties);
                    }
                };

                return ITEM_REGISTRY.register(args[0].toString(), supplier);
            }
        };

        ICallBack Blockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                DeferredHolder<Block, ?> b = BLOCK_REGISTRY.register(args[0].toString(), new Supplier<Block>() {
                    @Override
                    public Block get() {
                        return Utils.typeCasterIClampedSingleCallBackafied(args[1], Block.class, BlockBehaviour.Properties.class).
                                accept(Utils.typeCasterSupplierfied(args[3], BlockBehaviour.Properties.class).get());
                    }
                });
                ITEM_REGISTRY.register(args[0].toString(), ()-> new BlockItem(b.get(), Utils.typeCasterSupplierfied(args[2], Item.Properties.class).get()));
                return b;
            }
        };

        ICallBack ItemlessBlockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Supplier<Block> s = new Supplier<Block>() {
                    @Override
                    public Block get() {
                        return Utils.typeCasterIClampedSingleCallBackafied(args[1], Block.class, BlockBehaviour.Properties.class).
                                accept(Utils.typeCasterSupplierfied(args[2], BlockBehaviour.Properties.class).get());
                    }
                };

                return BLOCK_REGISTRY.register(args[0].toString(), s);
            }
        };


        ICallBack POIcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return POI_REGISTRY.register(args[0].toString(), ()-> new PoiType(ImmutableSet.copyOf(Utils.getDeferredHolderObject(args[3], Block.class).get().getStateDefinition().getPossibleStates()), 1, 1));
            }
        };


        ICallBack Professioncallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                TagKey<PoiType> poi =  TagKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(modid, args[1].toString()));
                return PROFESSION_REGISTRY.register(args[0].toString(), ()->
                        new VillagerProfession(modid+"."+args[0].toString(),
                            entry-> entry.is(poi),
                            entry-> entry.is(poi),
                            Utils.nullSafeGetter(args[2], ImmutableSet::of, ()-> ImmutableSet.copyOf(Utils.typeCaster(args[2], Item[].class))),
                            Utils.nullSafeGetter(args[3], ImmutableSet::of, ()-> ImmutableSet.copyOf(Utils.typeCaster(args[3], Block[].class))),
                            Utils.nullSafeGetter(args[4], null, ()-> Utils.typeCaster(args[4], SoundEvent.class))
                        )
                );
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
                return RECIPE_SERIALIZER_REGISTRY.register(args[0].toString(), ()-> Utils.typeCaster(args[1], RecipeSerializer.class));
            }
        };

        ICallBack RecipeTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return RECIPE_TYPE_REGISTRY.register(args[0].toString(), ()-> Utils.typeCaster(args[1], RecipeType.class));
            }
        };

        ICallBack CreativeModeTabcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ITEM_GROUP_REGISTRY.register(args[0].toString(), Utils.typeCasterSupplierfied(args[1], CreativeModeTab.class));
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

    public static void CreateCommonSetup(final FMLCommonSetupEvent event, String modid) {
        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                AzureArmor tranformed_obj = Utils.convertToMcSafeType(args[0]);

                AzArmorRendererRegistry.register(Utils.typeCasterSupplierfied(tranformed_obj.GetAzureRenderer(), AzArmorRenderer.class), tranformed_obj.asItem());

                return null;
            }
        }, BootstrapType.AZURE_ARMOR.getTypeId(),  modid);


        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                AzureItem tranformed_obj = Utils.convertToMcSafeType(args[0]);
                AzItemRendererRegistry.register(Utils.typeCasterSupplierfied(tranformed_obj.GetAzureRenderer(), AzItemRenderer.class), tranformed_obj);
                return null;
            }
        }, BootstrapType.AZURE_ITEM.getTypeId(),  modid);


        Bootstrapper.initBootstrapper(new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Class clazz = Utils.typeCaster(args[1]);

                if (ReflectionUtil.isSubclassOrSame(clazz, AzureArmor.class)) {
                    AzureArmor tranformed_obj = Utils.convertToMcSafeType(args[0]);
                    AzIdentityRegistry.register(tranformed_obj);
                }

                if (ReflectionUtil.isSubclassOrSame(clazz, AzureItem.class)) {
                    AzureItem tranformed_obj = Utils.convertToMcSafeType(args[0]);
                    AzIdentityRegistry.register(tranformed_obj);
                }

                return null;
            }
        }, BootstrapType.AZURE_ID.getTypeId(),  modid);
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
        /*builder.onAdd((r)->Bootstrapper.initBootstrapper((args)->{
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
                        new ConfigurationBuilder().
                                forPackage(args[0].toString()).
                                filterInputsBy(new FilterBuilder().includePackage(args[0].toString())).
                                setScanners(Scanners.FieldsAnnotated));
                return reflections.getFieldsAnnotatedWith((Class<? extends Annotation>) args[1]);
            }
        };

        AutoRegistryLoader.collectAnnotatedTypesForMod = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Reflections reflections = new Reflections(
                        new ConfigurationBuilder().
                                forPackage(args[0].toString()).
                                filterInputsBy(new FilterBuilder().includePackage(args[0].toString())).
                                setScanners(Scanners.TypesAnnotated));
                return reflections.getTypesAnnotatedWith((Class<? extends Annotation>) args[1]);
            }
        };

        AutoRegistryLoader.entityTypeBlockSelector = new ICallBack() {

            @Override
            public Object accept(Object... args) {
                return Utils.typeCaster(
                                    Utils.typeCaster(args[0], BlockEntity.class).
                                    getArgs().
                                    get("block"),
                            org.vrglab.vrglabsLib.api.autoRegistry.World.Block.class).
                        getRawData();
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
