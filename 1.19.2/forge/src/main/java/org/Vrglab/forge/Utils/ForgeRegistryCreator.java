package org.Vrglab.forge.Utils;

import com.google.common.collect.ImmutableSet;
import dev.architectury.hooks.level.biome.BiomeProperties;
import dev.architectury.registry.level.biome.BiomeModifications;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Networking.Network;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;


public class ForgeRegistryCreator {


   public static ICallBack TradeRegistryEventCallback = new ICallBack() {
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


        Registry.initRegistry(Itemcallback, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockcallback, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(Blockcallback, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIcallback, RegistryTypes.POI, modid);
        Registry.initRegistry(Professioncallback, RegistryTypes.PROFESSION, modid);
        Registry.initRegistry(OreConfiguredFeatCallBack, RegistryTypes.CONFIGURED_FEAT_ORE, modid);
        Registry.initRegistry(PlacedFeatCallBack, RegistryTypes.PLACED_FEAT, modid);
    }

    public static void villagerTradeEventResolver(VillagerTradesEvent e, String modid) {
        Registry.ForgeEventResolver(e, TradeRegistryEventCallback, RegistryTypes.TRADE, modid);
    }

}
