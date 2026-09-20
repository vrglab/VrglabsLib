package org.vrglab.vrglabsLib.api.autoRegistry.World;


import net.minecraft.world.level.block.entity.BlockEntityType;
import org.vrglab.vrglabsLib.api.functionProviders.IBlockEntityLoaderFunction;

import java.util.HashMap;
import java.util.function.Supplier;

public class BlockEntity<T extends net.minecraft.world.level.block.entity.BlockEntity> extends AutoRegistryObject<BlockEntityType<T>> {

    public BlockEntity(String modid, IBlockEntityLoaderFunction aNew, org.vrglab.vrglabsLib.api.autoRegistry.World.Block block) {
        this.modid = modid;

        this.args = new HashMap<>();
        this.args.put("new", aNew);
        this.args.put("block", block);
        this.args.put("supplier", new Supplier<BlockEntityType<T>>() {
            @Override
            public BlockEntityType<T> get() {
                return registeredObject;
            }
        });
    }
}
