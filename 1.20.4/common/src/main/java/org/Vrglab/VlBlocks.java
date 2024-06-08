package org.Vrglab;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;

public class VlBlocks {

    public static Object BLOCK = Registry.RegisterBlock("block", Modinfo.MOD_ID, ()->new Block(AbstractBlock.Settings.create()), new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS));

    public static void init(){

    }
}
