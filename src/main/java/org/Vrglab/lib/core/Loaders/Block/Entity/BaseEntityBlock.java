package org.Vrglab.lib.core.Loaders.Block.Entity;

import org.Vrglab.lib.core.Loaders.Block.IBlockType;

public abstract class BaseEntityBlock<t extends BlockEntity> extends net.minecraft.world.level.block.BaseEntityBlock implements IBlockType {

    protected BaseEntityBlock(Properties p_49224_) {
        super(p_49224_);
    }
}
