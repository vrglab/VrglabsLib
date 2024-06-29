package org.TestSystem.world.Blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import org.TestSystem.TestMod;
import org.Vrglab.Modloader.Registration.Registry;

public class TestBlocks {

    public static Object TEST_BLOCK = Registry.RegisterBlock("test_block", TestMod.MODID, ()->new Block(AbstractBlock.Settings.of(Material.METAL)), TestMod.basicItemSettings());
    public static Object TEST_ITEMLESS_BLOCK = Registry.RegisterItemlessBlock("test_itemless_block", TestMod.MODID, ()->new Block(AbstractBlock.Settings.of(Material.METAL)));

    public static Object TEST_POI_BLOCK = Registry.RegisterBlock("test_poi_block", TestMod.MODID, ()->new Block(AbstractBlock.Settings.of(Material.METAL)), TestMod.basicItemSettings());
    public static Object TEST_ENTITY_BLOCK = Registry.RegisterBlock("test_entity_block", TestMod.MODID, ()->new TestEntityBlockHolder(AbstractBlock.Settings.of(Material.METAL)), TestMod.basicItemSettings());

    public static Object TEST_ENTITY_BLOCK_TYPE = Registry.RegisterBlockEntityType("test_entity_block_type", TestMod.MODID, TestBlockEntity::new, TEST_ENTITY_BLOCK);

    public static void init() {

    }
}
