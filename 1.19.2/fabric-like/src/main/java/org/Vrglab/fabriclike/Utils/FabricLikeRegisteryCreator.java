package org.Vrglab.fabriclike.Utils;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;
import org.Vrglab.Modloader.enumTypes.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;
import org.Vrglab.Networking.Network;

import java.util.List;
import java.util.function.Supplier;

public class FabricLikeRegisteryCreator {

    public static void Create(String modid) {
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


        TypeTransformer.ObjectToType = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return args[0];
            }
        };



        Network.registerGlobalReceiver = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                ServerPlayNetworking.registerGlobalReceiver((Identifier) args[0], (a,b,c,d,e)->((ICallBack)args[1]).accept(a,b,c,d,e));
            }
        };

        Network.clientSendPacket = new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
                ClientPlayNetworking.send((Identifier) args[0], (PacketByteBuf) args[1]);
            }
        };

        ICallBack ItemRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(modid, args[0].toString()), ((Supplier<Item>)args[1]).get());
            }
        };
        ICallBack BlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Block b = net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK, new Identifier(modid, args[0].toString()), ((Supplier<Block>)args[1]).get());
                net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.ITEM, new Identifier(modid, args[0].toString()), new BlockItem(b, (Item.Settings) args[2]));
                return b;
            }
        };
        ICallBack ItemlessBlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK, new Identifier(modid, args[0].toString()), ((Supplier<Block>)args[1]).get());
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
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.VILLAGER_PROFESSION, new Identifier(modid, args[0].toString()), VillagerProfessionBuilder.create().id(new Identifier(modid, args[0].toString())).workstation(RegistryKey.of(net.minecraft.util.registry.Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(modid, args[1].toString()))).harvestableItems(args[2] == null ? null: (Item[])args[2]).secondaryJobSites(args[3] == null ? null: (Block[])args[3]).workSound((args.length >= 5 && args[4] == null) ? null : (SoundEvent)args[4]).build());
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
                return ConfiguredFeatures.register(args[0].toString(), (Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(), (int)args[3]));
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return PlacedFeatures.register(args[0].toString(), (RegistryEntry<? extends ConfiguredFeature<?, ?>>) args[1], (List<PlacementModifier>) args[2]);
            }
        };

        ICallBack BiomeModCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                VinillaBiomeTypes types = (VinillaBiomeTypes) args[1];
                switch (types){
                    case END -> BiomeModifications.addFeature(BiomeSelectors.foundInTheEnd(), (GenerationStep.Feature) args[2], ((RegistryEntry<PlacedFeature>) args[3]).getKey().get());
                    case NETHER -> BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), (GenerationStep.Feature) args[2], ((RegistryEntry<PlacedFeature>) args[3]).getKey().get());
                    case OVERWORLD -> BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), (GenerationStep.Feature) args[2], ((RegistryEntry<PlacedFeature>) args[3]).getKey().get());
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
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.BLOCK_ENTITY_TYPE, new Identifier(modid, args[0].toString()), FabricBlockEntityTypeBuilder.create(factory, (Block)args[2]).build());
            }
        };

        Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(BlockEntityTypeRegistryCallBack, RegistryTypes.BLOCK_ENTITY_TYPE, modid);
        Registry.initRegistry(POIRegistryCallBack, RegistryTypes.POI, modid);
        Registry.initRegistry(ProfesionRegistryCallBack, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(TradeRegistryCallBack, RegistryTypes.TRADE, modid);
        Registry.initRegistry(OreGenRegistryCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        Registry.initRegistry(BiomeModCallBack, RegistryTypes.BIOME_MODIFICATIONS, modid);
    }
}
