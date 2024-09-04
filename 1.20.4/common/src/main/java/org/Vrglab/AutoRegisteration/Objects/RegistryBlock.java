package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryBlock<T extends Block> extends AutoRegisteryObject<T> {

    public RegistryBlock(String modid, Supplier<Item.Settings> settings, Supplier<T> getBlock) {
        this.supplier = getBlock;
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("item.settings", settings);
    }

    public RegistryBlock(String modid, Item.Settings settings, Supplier<T> getBlock) {
        this.supplier = getBlock;
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("item.settings", new Supplier<Item.Settings>() {
            @Override
            public Item.Settings get() {
                return settings;
            }
        });
    }
}
