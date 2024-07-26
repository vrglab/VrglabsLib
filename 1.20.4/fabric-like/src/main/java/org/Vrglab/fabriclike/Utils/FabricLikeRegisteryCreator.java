package org.Vrglab.fabriclike.Utils;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.Vrglab.AutoRegisteration.AutoRegistryLoader;
import org.Vrglab.EnergySystem.EnergyStorage;
import org.Vrglab.EnergySystem.EnergyStorageUtils;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Registration.Bootstrapper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Modloader.enumTypes.*;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Networking.Network;
import org.Vrglab.Reflections.Reflections;
import org.Vrglab.Reflections.scanners.Scanners;
import org.Vrglab.Reflections.util.ConfigurationBuilder;
import org.Vrglab.Reflections.util.FilterBuilder;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Supplier;

public class FabricLikeRegisteryCreator {

    public static void Create(String modid) {
        createAutoRegistry();
        setEnergyStorageStatics(modid);
        setOreGenHelperStatics();
        setNetworkStatics();

        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return args[0];
            }
        };

        ICallBack ItemRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.ITEM, new Identifier(modid, args[0].toString()), ((Supplier<Item>)args[1]).get());
            }
        };
        ICallBack BlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Block b = net.minecraft.registry.Registry.register(Registries.BLOCK, new Identifier(modid, args[0].toString()), ((Supplier<Block>)args[1]).get());
                net.minecraft.registry.Registry.register(Registries.ITEM, new Identifier(modid, args[0].toString()), new BlockItem(b, ((Supplier<Item.Settings>) args[2]).get()));
                return b;
            }
        };
        ICallBack ItemlessBlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.BLOCK, new Identifier(modid, args[0].toString()), ((Supplier<Block>)args[1]).get());
            }
        };
        ICallBack POIRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return PointOfInterestHelper.register(new Identifier(modid, args[0].toString()), (int)args[1], (int)args[2], (Block)args[3]);
            }
        };

        ICallBack ProfesionRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(modid, args[0].toString()),
                        VillagerProfessionBuilder.create().id(new Identifier(modid, args[0].toString()))
                                .workstation(RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(modid, args[1].toString())))
                                .harvestableItems(args[2] == null ? null: (Item[])args[2]).secondaryJobSites(args[3] == null ? null: (Block[])args[3])
                                .workSound((args.length >= 5 && args[4] == null) ? null : (SoundEvent)args[4]).build());
            }
        };

        ICallBack TradeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                TradeOfferHelper.registerVillagerOffers((VillagerProfession)args[1] ,(int)args[2],
                        factories -> {
                            for (TradeOffer data: (TradeOffer[])args[3]) {
                                factories.add(((entity, random) -> data));
                            }
                        });
                return null;
            }
        };

        ICallBack OreGenRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey r = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(modid, args[0].toString()));
                Bootstrapper.SimpleRegister(BootstrapType.CONFIGUERED_FEAT_ORES, modid, r, new ConfiguredFeature((Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(),  (int)args[3])));
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

        ICallBack BiomeModCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                VinillaBiomeTypes types = (VinillaBiomeTypes) args[1];
                switch (types){
                    case END -> BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                    case NETHER -> BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                    case OVERWORLD -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), (GenerationStep.Feature) args[2], (RegistryKey<PlacedFeature>) args[3]);
                }
                return null;
            }
        };

        ICallBack BlockEntityTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                FabricBlockEntityTypeBuilder.Factory factory = new FabricBlockEntityTypeBuilder.Factory() {
                    @Override
                    public BlockEntity create(BlockPos blockPos, BlockState blockState) {
                        return ((IBlockEntityLoaderFunction)args[1]).create(blockPos, blockState);
                    }
                };
                return net.minecraft.registry.Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(modid, args[0].toString()), FabricBlockEntityTypeBuilder.create(factory, (Block)args[2]).build());
            }
        };


        ICallBack RecipeSerializerRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(modid, args[0].toString()), (RecipeSerializer)args[1]);
            }
        };

        ICallBack RecipeTypeRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.RECIPE_TYPE, new Identifier(modid, args[0].toString()), (RecipeType) args[1]);
            }
        };

        ICallBack ItemGroupRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.ITEM_GROUP, new Identifier(modid, args[0].toString()), (ItemGroup)args[1]);
            }
        };

        Registry.initRegistry(ItemGroupRegistryCallBack, RegistryTypes.CREATIVE_MODE_TAB, modid);
        Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(BlockEntityTypeRegistryCallBack, RegistryTypes.BLOCK_ENTITY_TYPE, modid);
        Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIRegistryCallBack, RegistryTypes.POI, modid);
        Registry.initRegistry(ProfesionRegistryCallBack, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(TradeRegistryCallBack, RegistryTypes.TRADE, modid);
        Registry.initRegistry(OreGenRegistryCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        Registry.initRegistry(BiomeModCallBack, RegistryTypes.BIOME_MODIFICATIONS, modid);
        Registry.initRegistry(RecipeSerializerRegistryCallBack, RegistryTypes.RECIPE_SERIALIZER, modid);
        Registry.initRegistry(RecipeTypeRegistryCallBack, RegistryTypes.RECIPE_TYPE, modid);
    }

    public static void configureBootstrappables(RegistryWrapper.WrapperLookup Wrapper, FabricDynamicRegistryProvider.Entries entries) {
        entries.addAll(Wrapper.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(Wrapper.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));
    }

    public static void boostrap(RegistryBuilder builder, String modid) {
        builder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
           return r.register((RegistryKey<ConfiguredFeature<?,?>>) args[0], (ConfiguredFeature<?, ?>)args[1]);
        }, BootstrapType.CONFIGUERED_FEAT_ORES, modid));


        builder.addRegistry(RegistryKeys.PLACED_FEATURE, (r)->Bootstrapper.initBootstrapper((args)->{
            var config_feat_lookup = r.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
            return r.register((RegistryKey<PlacedFeature>)args[0], new PlacedFeature(config_feat_lookup.getOrThrow((RegistryKey<ConfiguredFeature<?, ?>>) args[1]), (List<PlacementModifier>)args[2]));
        }, BootstrapType.PLACED_FEAT, modid));
    }

    private static void setEnergyStorageStatics(String modid) {
        EnergyStorageUtils.createStorageInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return new SimpleEnergyStorage((Long) args[0],(Long)  args[1],(Long)  args[2]){
                    @Override
                    protected void onFinalCommit() {
                        super.onFinalCommit();
                        try {
                            ((EnergyStorage)args[4]).makeDirty.accept();
                        } catch (Throwable t) {

                        }
                    }
                };
            }
        };

        EnergyStorageUtils.receiveEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                try (Transaction openTrans = Transaction.openOuter()) {
                    long res = ((SimpleEnergyStorage)args[0]).insert((long)args[1], openTrans);
                    openTrans.commit();
                    return res;
                }
            }
        };

        EnergyStorageUtils.extractEnergyInstance = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                try (Transaction openTrans = Transaction.openOuter()) {
                    long res = ((SimpleEnergyStorage)args[0]).extract((long)args[1], openTrans);
                    openTrans.commit();
                    return res;
                }
            }
        };

        EnergyStorageUtils.hasExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                BlockEntity entity = (BlockEntity) args[0];

                if(entity == null){
                    return false;
                }

                return (team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.NORTH) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.EAST) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.WEST) != null ||
                        team.reborn.energy.api.EnergyStorage.SIDED.find(entity.getWorld(), entity.getPos(), Direction.SOUTH) != null);
            }
        };

        EnergyStorageUtils.wrapExternalStorage = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                team.reborn.energy.api.EnergyStorage storage = team.reborn.energy.api.EnergyStorage.SIDED.find((World) args[0], (BlockPos) args[1], (Direction) args[2]);
                return new EnergyStorage(storage, storage.getCapacity(), storage.getCapacity(), storage.getCapacity(), storage.getAmount());
            }
        };
    }

    private static void setOreGenHelperStatics() {
        OreGenFeatCreationHelper.ObjectBlockToStateConverted = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return ((Block)args[0]).getDefaultState();
            }
        };

        PlacementModifierCreationHelper.getHeightModifications = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return HeightRangePlacementModifier.trapezoid(YOffset.aboveBottom((Integer) args[0]), YOffset.aboveBottom((Integer) args[1]));
            }
        };
    }

    private static void setNetworkStatics() {
        Network.registerGlobalReceiver = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                ServerPlayNetworking.registerGlobalReceiver((Identifier) args[0], (a, b, c, d, e)->((ICallBack)args[1]).accept(a,b,c,d,e));
            }
        };

        Network.clientSendPacket = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                ClientPlayNetworking.send((Identifier) args[0], (PacketByteBuf) args[1]);
            }
        };
    }

    private static void createAutoRegistry() {
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
