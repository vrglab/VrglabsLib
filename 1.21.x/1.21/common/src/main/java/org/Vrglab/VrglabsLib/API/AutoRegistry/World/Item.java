package org.Vrglab.VrglabsLib.API.AutoRegistry.World;


import org.Vrglab.VrglabsLib.API.Callbacks.IClampedCallBack;

import java.util.HashMap;
import java.util.function.Supplier;

public class Item<T extends net.minecraft.world.item.Item> extends AutoRegistryObject<T> {
    public Item(String modid, IClampedCallBack<T> getItem, Supplier<Properties> settingsItem) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("item", getItem);
        this.args.put("settingsItem", settingsItem);
    }


    public static class Properties extends net.minecraft.world.item.Item.Properties {

    }
}
