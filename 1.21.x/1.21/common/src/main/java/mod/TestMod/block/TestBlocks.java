package mod.TestMod.block;

import mod.TestMod.TestModeEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterBlock;
import org.vrglab.vrglabsLib.api.autoRegistry.World.Block;

public class TestBlocks {

    @RegisterBlock(Name = "testblock")
    public static Block TEST_BLOCK = new Block<>(TestModeEntry.MODID,
            ()->new Item.Properties(),
            (prop)->new net.minecraft.world.level.block.Block(prop),
            ()-> BlockBehaviour.Properties.of());

    public static void Init(){

    }
}
