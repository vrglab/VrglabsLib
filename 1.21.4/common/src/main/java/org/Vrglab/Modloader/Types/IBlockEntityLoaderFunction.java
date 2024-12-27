package org.Vrglab.Modloader.Types;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

@FunctionalInterface
public interface IBlockEntityLoaderFunction <T extends BlockEntity> {
    T create(BlockPos blockPos, BlockState blockState);
}
