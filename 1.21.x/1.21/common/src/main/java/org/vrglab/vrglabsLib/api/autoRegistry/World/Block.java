package org.vrglab.vrglabsLib.api.autoRegistry.World;


import net.minecraft.world.item.Item;
import org.vrglab.vrglabsLib.api.callbacks.IClampedSingleCallback;

import java.util.HashMap;
import java.util.function.Supplier;

public class Block<T extends net.minecraft.world.level.block.Block> extends AutoRegistryObject<T> {

    public Block(String modid, Supplier<Item.Properties> settings, IClampedSingleCallback<T, net.minecraft.world.level.block.state.BlockBehaviour.Properties> getBlock, Supplier<net.minecraft.world.level.block.state.BlockBehaviour.Properties> blocksettings) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("block", getBlock);
        this.args.put("block.settings", blocksettings);
        this.args.put("item.settings", settings);
    }

    public abstract class BlockBehaviour extends net.minecraft.world.level.block.state.BlockBehaviour {

        public BlockBehaviour(Properties pProperties) {
            super(pProperties);
        }
    }
}
