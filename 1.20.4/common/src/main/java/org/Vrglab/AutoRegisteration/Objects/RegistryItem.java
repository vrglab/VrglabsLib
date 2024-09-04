package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryItem<T extends Item> extends AutoRegisteryObject<T> {
    public RegistryItem(String modid, Supplier<T> getItem) {
        super(modid);
        this.supplier = getItem;
    }
}
