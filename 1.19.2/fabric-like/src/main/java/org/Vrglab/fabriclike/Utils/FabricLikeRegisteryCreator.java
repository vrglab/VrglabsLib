package org.Vrglab.fabriclike.Utils;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.villager.VillagerProfessionBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;

import java.util.function.Supplier;

public class FabricLikeRegisteryCreator {

    public static void Create(String modid) {
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
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.POINT_OF_INTEREST_TYPE, new Identifier(modid, args[0].toString()), new PointOfInterestType(ImmutableSet.copyOf(((Block)args[1]).getStateManager().getStates()), 1,1));
            }
        };

        ICallBack ProfesionRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.util.registry.Registry.register(net.minecraft.util.registry.Registry.VILLAGER_PROFESSION, new Identifier(modid, args[0].toString()), VillagerProfessionBuilder.create().id(new Identifier(modid, args[0].toString())).workstation(RegistryKey.of(net.minecraft.util.registry.Registry.POINT_OF_INTEREST_TYPE_KEY, new Identifier(modid, args[1].toString()))).harvestableItems(args[2] == null ? null: (Item[])args[2]).secondaryJobSites(args[3] == null ? null: (Block[])args[3]).workSound((args.length >= 5 && args[4] == null) ? null : (SoundEvent)args[4]).build());
            }
        };

        Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
        Registry.initRegistry(POIRegistryCallBack, RegistryTypes.POI, modid);
        Registry.initRegistry(ProfesionRegistryCallBack, RegistryTypes.PROFESSION, modid);
    }
}
