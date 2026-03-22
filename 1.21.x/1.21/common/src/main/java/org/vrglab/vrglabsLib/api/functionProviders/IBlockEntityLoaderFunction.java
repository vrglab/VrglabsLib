package org.vrglab.vrglabsLib.api.functionProviders;


import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface IBlockEntityLoaderFunction<T extends BlockEntity> {
    T create(BlockPos blockPos, BlockState blockState);
}
