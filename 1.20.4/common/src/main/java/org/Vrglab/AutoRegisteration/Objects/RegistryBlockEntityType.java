package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Types.IBlockEntityLoaderFunction;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryBlockEntityType<T extends BlockEntity> extends AutoRegisteryObject<BlockEntityType<T>>{

    public RegistryBlockEntityType(String modid, IBlockEntityLoaderFunction aNew, RegistryBlock block) {
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

    public void setRegistryData(Object data) {
        rawData = data;
    }

    @Override
    public BlockEntityType<T> getRegisteredObject() {
        return (BlockEntityType<T>)TypeTransformer.ObjectToType.accept(rawData);
    }
}
