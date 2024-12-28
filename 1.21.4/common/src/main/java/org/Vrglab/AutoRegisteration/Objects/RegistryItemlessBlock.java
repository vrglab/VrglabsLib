package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.IClampedCallBack;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryItemlessBlock <T extends Block> extends AutoRegisteryObject<T> {

    public RegistryItemlessBlock(String modid, IClampedCallBack<T> getBlock, Supplier<AbstractBlock.Settings> blocksettings) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("block", getBlock);
        this.args.put("block.settings", blocksettings);
    }

    public void setRegisteredObject(){
        this.registeredObject = supplier.get();
    }
}
