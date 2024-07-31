package org.TestSystem.world.Blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import org.TestSystem.TestMod;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBlock;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBlockEntityType;
import org.Vrglab.AutoRegisteration.Annotations.RegisterItemlessBlock;
import org.Vrglab.AutoRegisteration.Objects.RegistryBlock;
import org.Vrglab.AutoRegisteration.Objects.RegistryBlockEntityType;
import org.Vrglab.AutoRegisteration.Objects.RegistryItemlessBlock;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;
import java.util.List;

public class TestBlocks {

    @RegisterBlock(Name = "test_block")
    public static RegistryBlock<Block> TEST_BLOCK = new RegistryBlock<>(TestMod.MODID, ()->TestMod.basicItemSettings(), ()->new Block(AbstractBlock.Settings.create()));

    @RegisterItemlessBlock(Name = "test_itemless_block")
    public static RegistryItemlessBlock<Block> TEST_ITEMLESS_BLOCK = new RegistryItemlessBlock<>(TestMod.MODID,  ()->new Block(AbstractBlock.Settings.create()));

    @RegisterBlock(Name = "test_poi_block")
    public static RegistryBlock<Block> TEST_POI_BLOCK = new RegistryBlock<>(TestMod.MODID, ()->TestMod.basicItemSettings(), ()->new Block(AbstractBlock.Settings.create()));

    @RegisterBlock(Name = "test_entity_block")
    public static RegistryBlock<TestEntityBlockHolder> TEST_ENTITY_BLOCK = new RegistryBlock<>(TestMod.MODID, ()->TestMod.basicItemSettings(), ()->new TestEntityBlockHolder(AbstractBlock.Settings.create()));

    @RegisterBlockEntityType(Name = "test_entity_block_type")
    public static RegistryBlockEntityType<TestBlockEntity> TEST_ENTITY_BLOCK_TYPE = new RegistryBlockEntityType<>(TestMod.MODID, TestBlockEntity::new, TEST_ENTITY_BLOCK);

    public static Object[] getAll(){
        List<Object> objectList = new ArrayList<>();
        objectList.add(TEST_BLOCK.getRawData());
        objectList.add(TEST_POI_BLOCK.getRawData());
        objectList.add(TEST_ENTITY_BLOCK.getRawData());
        return objectList.toArray();
    }

    public static void init() {

    }
}
