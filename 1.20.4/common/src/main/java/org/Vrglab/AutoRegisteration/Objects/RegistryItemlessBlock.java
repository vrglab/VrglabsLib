package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryItemlessBlock <T extends Block> extends AutoRegisteryObject<T> {

    public RegistryItemlessBlock(String modid, Supplier<T> getBlock) {
        this.supplier = getBlock;
        this.modid = modid;
        this.args = new HashMap<>();
    }

    public void setRegisteredObject(){
        this.registeredObject = supplier.get();
    }
}
