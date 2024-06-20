package org.Vrglab.forge.Utils;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Interner;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.EnergySystem.EnergyStorage;
import org.Vrglab.EnergySystem.EnergyStorageUtils;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.*;
import org.Vrglab.Modloader.enumTypes.RegistryTypes;
import org.Vrglab.Networking.Network;
import org.Vrglab.Utils.VLModInfo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;


public class ForgeRegistryCreator {


   private static ICallBack TradeRegistryEventCallback = new ICallBack() {
        @Override
        public Object accept(Object... args) {
            Object[] arg = (Object[]) args[0];
            if(((VillagerTradesEvent)args[1]).getType() == ((RegistryObject<VillagerProfession>)arg[1]).get()) {
                Int2ObjectMap<List<TradeOffers.Factory>> trades = ((VillagerTradesEvent)args[1]).getTrades();
                for (TradeOffer data: (TradeOffer[])arg[3]) {
                    trades.get((int)arg[2]).add((trader, rand) -> data);
                }
            }
            return null;
        }
   };

    public static void Create(IEventBus eventBus, String modid) {

        createEnergyCallBacks();
        createOreGenStatics();
        createNetworkStatics();

        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                try {
                    return ((RegistryObject)args[0]).get();
                } catch (Throwable t) {
                    return args[0];
                }
            }
        };

        DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        ITEM_REGISTRY.register(eventBus);


        DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        BLOCK_REGISTRY.register(eventBus);

        DeferredRegister<PointOfInterestType> POI_REGISTRY = DeferredRegister.create(ForgeRegistries.POI_TYPES, modid);
        POI_REGISTRY.register(eventBus);

        DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, modid);
        PROFESSION_REGISTRY.register(eventBus);

        DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEAT = DeferredRegister.create(net.minecraft.util.registry.Registry.CONFIGURED_FEATURE_KEY, modid);
        CONFIGURED_FEAT.register(eventBus);

        DeferredRegister<PlacedFeature> PLACED_FEAT = DeferredRegister.create(net.minecraft.util.registry.Registry.PLACED_FEATURE_KEY, modid);
        PLACED_FEAT.register(eventBus);

        DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, modid);
        BLOCK_ENTITY_TYPE.register(eventBus);

        DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZER_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, modid);
        RECIPE_SERIALIZER_REGISTRY.register(eventBus);

        DeferredRegister<RecipeType<?>> RECIPE_TYPE_REGISTRY = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, modid);
        RECIPE_TYPE_REGISTRY.register(eventBus);

        ICallBack Itemcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ITEM_REGISTRY.register(args[0].toString(), (Supplier<? extends Item>) args[1]);
            }
        };

        ICallBack Blockcallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
               RegistryObject<Block> b = BLOCK_REGISTRY.register(args[0].toString(), (Supplier<? extends Block>) args[1]);
               ITEM_REGISTRY.register(args[0].toString(), ()->new BlockItem(b.get(), (Item.Settings) args[2]));
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
                return POI_REGISTRY.register(args[0].toString(), ()-> new PointOfInterestType(ImmutableSet.copyOf(((RegistryObject<Block>)args[3]).get().getStateManager().getStates()), 1,1));
            }
        };


        ICallBack Professioncallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey<PointOfInterestType> poi =  RegistryKey.of(net.minecraft.util.registry.Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(modid, args[1].toString()));
                return PROFESSION_REGISTRY.register(args[0].toString(), ()->new VillagerProfession(modid+"."+args[0].toString(), entry->entry.matchesKey(poi), entry->entry.matchesKey(poi), args[2] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Item[])args[2])), args[3] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Block[])args[3])), args[4] == null ? null : (SoundEvent) args[4]));
            }
        };

        ICallBack OreConfiguredFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return CONFIGURED_FEAT.register(args[0].toString(), ()-> new ConfiguredFeature((Feature)args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(), (int)args[3])));
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return PLACED_FEAT.register(args[0].toString(), ()->new PlacedFeature(((RegistryObject<ConfiguredFeature<?, ?>>) args[1]).getHolder().get(), (List<PlacementModifier>) args[2]));
            }
        };

        ICallBack BlockEntityTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return BLOCK_ENTITY_TYPE.register(args[0].toString(), ()->BlockEntityType.Builder.create((blockPos,blockState)->((IBlockEntityLoaderFunction)args[1]).create(blockPos, blockState), ((Block)((RegistryObject)args[2]).get())).build(null));
            }
        };

        ICallBack ScreenHandlerTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                ScreenHandlerType.Factory factory = new ScreenHandlerType.Factory() {
                    @Override
                    public ScreenHandler create(int syncId, PlayerInventory playerInventory) {
                        return ((IScreenHandlerTypeCreationFunction)args[1]).create(syncId, playerInventory);
                    }
                };
                return new ScreenHandlerType<>(factory);
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


        Registry.initRegistry(Itemcallback, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockcallback, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(Blockcallback, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(BlockEntityTypeRegistryCallBack, RegistryTypes.BLOCK_ENTITY_TYPE, modid);
        Registry.initRegistry(ScreenHandlerTypeRegistryCallBack, RegistryTypes.SCREEN_HANDLER_TYPE, modid);
        Registry.initRegistry(POIcallback, RegistryTypes.POI, modid);
        Registry.initRegistry(Professioncallback, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(OreConfiguredFeatCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        Registry.initRegistry(RecipeSerializerRegistryCallBack, RegistryTypes.RECIPE_SERIALIZER, modid);
        Registry.initRegistry(RecipeTypeRegistryCallBack, RegistryTypes.RECIPE_TYPE, modid);
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

    public static void villagerTradeEventResolver(VillagerTradesEvent e, String modid) {
        Registry.ForgeEventResolver(e, TradeRegistryEventCallback, RegistryTypes.TRADE, modid);
    }

    private static void createEnergyCallBacks() {
        EnergyStorageUtils.createStorageInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return new net.minecraftforge.energy.EnergyStorage(Math.toIntExact((long)args[0]), Math.toIntExact((long)args[1]), Math.toIntExact((long)args[2]), Math.toIntExact((long)args[3])) {
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
                return ((net.minecraftforge.energy.IEnergyStorage)args[0]).receiveEnergy(Math.toIntExact((long)args[1]), (boolean)args[2]);
            }
        };

        EnergyStorageUtils.extractEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((net.minecraftforge.energy.IEnergyStorage)args[0]).extractEnergy(Math.toIntExact((long)args[1]), (boolean)args[2]);
            }
        };

        EnergyStorageUtils.hasExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return  ((((BlockEntity)args[0]) != null) && ((BlockEntity)args[0]) instanceof ICapabilityProvider)||((((BlockEntity)args[0]) != null) && ((BlockEntity)args[0]).getCapability(ForgeCapabilities.ENERGY).resolve().get() != null);
            }
        };

        EnergyStorageUtils.wrapExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                net.minecraftforge.energy.IEnergyStorage storage = ((BlockEntity)args[3]).getCapability(ForgeCapabilities.ENERGY).resolve().get();
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
        };
    }

    private static void createOreGenStatics() {

        OreGenFeatCreationHelper.ObjectBlockToStateConverted = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((RegistryObject<Block>)args[0]).get().getDefaultState();
            }
        };

        PlacementModifierCreationHelper.getHeightModifications = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom((int) args[0]), YOffset.aboveBottom((int) args[1]));
            }
        };
    }

    private static void createNetworkStatics() {
        Network.registerGlobalReceiver = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {

            }
        };

        Network.clientSendPacket = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {

            }
        };
    }
}
