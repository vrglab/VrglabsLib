package org.Vrglab.fabriclike.Utils;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Bootstrapper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.enumTypes.*;
import org.Vrglab.Modloader.Types.ICallBack;

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
                net.minecraft.registry.Registry.register(Registries.ITEM, new Identifier(modid, args[0].toString()), new BlockItem(b, (Item.Settings) args[2]));
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
                return Bootstrapper.SimpleRegister(BootstrapType.CONFIGUERED_FEAT_ORES, modid, new ConfiguredFeature((Feature) args[1], new OreFeatureConfig(((Supplier<List<OreFeatureConfig.Target>>) args[2]).get(),  (int)args[3])));
            }
        };

        ICallBack PlacedFeatCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return Bootstrapper.SimpleRegister(BootstrapType.PLACED_FEAT, modid,args[1], args[2]);
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

        Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIRegistryCallBack, RegistryTypes.POI, modid);
        Registry.initRegistry(ProfesionRegistryCallBack, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(TradeRegistryCallBack, RegistryTypes.TRADE, modid);
        Registry.initRegistry(OreGenRegistryCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
        Registry.initRegistry(BiomeModCallBack, RegistryTypes.BIOME_MODIFICATIONS, modid);
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
            return r.register((RegistryKey<PlacedFeature>)args[0], new PlacedFeature(config_feat_lookup.getOrThrow((RegistryKey<ConfiguredFeature<?, ?>>) args[0]), (List<PlacementModifier>)args[1]));
        }, BootstrapType.CONFIGUERED_FEAT_ORES, modid));
    }
}
