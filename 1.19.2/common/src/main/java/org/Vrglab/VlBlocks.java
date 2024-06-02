package org.Vrglab;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;

public class VlBlocks {

    public static Object BLOCK = Registry.RegisterBlock("block", Modinfo.MOD_ID, ()->new Block(AbstractBlock.Settings.of(Material.METAL)), new Item.Settings().group(ItemGroup.DECORATIONS));

    public static void init(){

    }
}
