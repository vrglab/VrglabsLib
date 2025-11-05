package org.Vrglab.VrglabsLib.API.AutoRegistry.World;


import net.minecraft.world.level.block.state.BlockBehaviour;
import org.Vrglab.VrglabsLib.API.Callbacks.IClampedCallBack;

import java.util.HashMap;
import java.util.function.Supplier;

public class ItemlessBlock <T extends Block> extends AutoRegistryObject<T> {

    public ItemlessBlock(String modid, IClampedCallBack<T> getBlock, Supplier<BlockBehaviour.Properties> blocksettings) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("block", getBlock);
        this.args.put("block.settings", blocksettings);
    }

    public void setRegisteredObject(){
        this.registeredObject = supplier.get();
    }
}
