package org.vrglab.azure.azurelib.world.Block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.vrglab.azure.azurelib.common.render.block.AzBlockEntityRenderer;


import java.util.List;
import java.util.function.Supplier;

public abstract class AzureEntityBlock extends BlockEntity {


    public AzureEntityBlock(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public abstract Supplier<? extends AzBlockEntityRenderer<?>> GetAzureRenderer();
}
