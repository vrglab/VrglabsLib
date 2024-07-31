package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryItem<T extends Item> extends AutoRegisteryObject<T> {
    public RegistryItem(String modid, Supplier<T> getItem) {
        this.supplier = getItem;
        this.modid = modid;
    }

    public void setRegisteredObject() {
        this.registeredObject = this.supplier.get();
    }
}
