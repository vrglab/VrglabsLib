package org.TestSystem.world.Blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.Vrglab.Utils.Utils;

public class TestBlockEntity extends BlockEntity {
    public TestBlockEntity(BlockPos pos, BlockState state) {
        super(Utils.convertToMcSafeType(TestBlocks.TEST_ENTITY_BLOCK_TYPE), pos, state);
    }


}
