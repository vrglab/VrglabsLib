package org.Vrglab.lib.core.Loaders.Block;

import net.minecraft.world.item.Item;

public abstract class Block extends net.minecraft.world.level.block.Block implements IBlockType{
    public Block(Properties p_49795_) {
        super(p_49795_);
    }

    public abstract Item.Properties GetItemProperties();
}
