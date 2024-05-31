package org.Vrglab.forge.Utils;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;

import java.util.Set;
import java.util.function.Supplier;

public class ForgeRegistryCreator {

    public static void Create(IEventBus eventBus, String modid) {
        DeferredRegister<Item> ITEM_REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, modid);
        ITEM_REGISTRY.register(eventBus);


        DeferredRegister<Block> BLOCK_REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, modid);
        BLOCK_REGISTRY.register(eventBus);

        DeferredRegister<PointOfInterestType> POI_REGISTRY = DeferredRegister.create(ForgeRegistries.POI_TYPES, modid);
        POI_REGISTRY.register(eventBus);

        DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, modid);
        PROFESSION_REGISTRY.register(eventBus);

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
                return POI_REGISTRY.register(args[0].toString(), ()-> new PointOfInterestType(ImmutableSet.copyOf(((Block)args[1]).getStateManager().getStates()), 1,1));
            }
        };


        ICallBack Professioncallback = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                RegistryKey<PointOfInterestType> poi =  RegistryKey.of(net.minecraft.util.registry.Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(modid, args[1].toString()));
                return PROFESSION_REGISTRY.register(args[0].toString(), ()->new VillagerProfession(modid+"."+args[0].toString(), entry->entry.matchesKey(poi), entry->entry.matchesKey(poi), args[2] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Item[])args[2])), args[3] == null ? ImmutableSet.of() : ImmutableSet.copyOf(((Block[])args[3])), args[4] == null ? null : (SoundEvent) args[4]));
            }
        };

        Registry.initRegistry(Itemcallback, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockcallback, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(Blockcallback, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIcallback, RegistryTypes.POI, modid);
        Registry.initRegistry(POIcallback, RegistryTypes.PROFESSION, modid);
    }
}
