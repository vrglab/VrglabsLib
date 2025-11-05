package org.Vrglab.VrglabsLib.API.AutoRegistry.World;


import net.minecraft.world.level.block.entity.BlockEntityType;
import org.Vrglab.VrglabsLib.API.FunctionProviders.IBlockEntityLoaderFunction;

import java.util.HashMap;
import java.util.function.Supplier;

public class BlockEntity<T extends net.minecraft.world.level.block.entity.BlockEntity> extends AutoRegistryObject<BlockEntityType<T>>{

    public BlockEntity(String modid, IBlockEntityLoaderFunction aNew, org.Vrglab.VrglabsLib.API.AutoRegistry.World.Block block) {
        this.modid = modid;
        supplier = new Supplier<BlockEntityType<T>>() {
            @Override
            public BlockEntityType<T> get() {
                return registeredObject;
            }
        };

        this.args = new HashMap<>();
        this.args.put("new", aNew);
        this.args.put("block", block);
    }
}
