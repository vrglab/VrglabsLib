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
            ()->new Item.Properties(),
            (prop)->new net.minecraft.world.level.block.Block(prop),
            ()-> BlockBehaviour.Properties.of());

    @RegisterItemlessBlock(Name = "testitemlessblock")
    public static Block TEST_ITEMLESS_BLOCK = new Block<>(TestModeEntry.MODID,
            (prop)->new net.minecraft.world.level.block.Block(prop),
            ()-> BlockBehaviour.Properties.of());

    public static void Init(){

    }
}
