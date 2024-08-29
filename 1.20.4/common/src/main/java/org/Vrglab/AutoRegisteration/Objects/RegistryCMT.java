package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.item.ItemGroup;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryCMT<T extends ItemGroup> extends AutoRegisteryObject<T> {

    public RegistryCMT(String modid, T group_built) {
        this.supplier = new Supplier<T>() {
            @Override
            public T get() {
                return (T)group_built;
            }
        };
        this.modid = modid;
        this.args = new HashMap<>();
        this.rawData = group_built;
    }
}
