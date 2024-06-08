package org.Vrglab.TestClasses;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.VLModInfo;

public class VlBlocks {

    public static Object BLOCK = Registry.RegisterBlock("block", VLModInfo.MOD_ID, ()->new TestBlock(AbstractBlock.Settings.of(Material.METAL)), new Item.Settings().group(ItemGroup.DECORATIONS));

    public static Object BLOCK_ENTITY_TYPE = Registry.RegisterBlockEntityType("block_entity", VLModInfo.MOD_ID, TestBlockEntity::new, BLOCK);

    public static void init(){

    }
}
