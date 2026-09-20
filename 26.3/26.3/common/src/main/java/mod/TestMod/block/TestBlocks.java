package mod.TestMod.block;

import mod.TestMod.TestModeEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterBlock;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterItemlessBlock;
import org.vrglab.vrglabsLib.api.autoRegistry.World.Block;

public class TestBlocks {

    @RegisterBlock(Name = "ruby_block")
    public static Block RUBY_BLOCK = new Block<>(TestModeEntry.MODID,
            Item.Properties::new,
            net.minecraft.world.level.block.Block::new,
            BlockBehaviour.Properties::of);

    @RegisterItemlessBlock(Name = "testitemlessblock")
    public static Block TEST_ITEMLESS_BLOCK = new Block<>(TestModeEntry.MODID,
            net.minecraft.world.level.block.Block::new,
            BlockBehaviour.Properties::of);

    public static void Init(){

    }
}
