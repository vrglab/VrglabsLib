package org.vrglab.vrglabsLib.api.autoRegistry.World;


import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;

import java.util.HashMap;
import java.util.function.Supplier;

public class Item<T extends net.minecraft.world.item.Item> extends AutoRegistryObject<T> {
    public Item(String modid, IClampedCallBack<T> getItem, Supplier<net.minecraft.world.item.Item.Properties> settingsItem) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("item", getItem);
        this.args.put("settingsItem", settingsItem);
    }

    public Item(String modid, IClampedCallBack<T> getItem, Supplier<net.minecraft.world.item.Item.Properties> settingsItem, Class<T> clazz) {
        this(modid, getItem, settingsItem);
        this.args.put("item.class", clazz);
    }


    public static class SimpleItem extends org.vrglab.vrglabsLib.api.autoRegistry.World.Item<net.minecraft.world.item.Item> {

        public SimpleItem(String modid, IClampedCallBack<net.minecraft.world.item.Item> getItem, Supplier<net.minecraft.world.item.Item.Properties> settingsItem) {
            super(modid, getItem, settingsItem);
        }
    }

    public static class AzureBasedArmor<T extends AzureArmor> extends org.vrglab.vrglabsLib.api.autoRegistry.World.Item<T> {

        public AzureBasedArmor(String modid, IClampedCallBack<T> getItem, Supplier<net.minecraft.world.item.Item.Properties> settingsItem, Class<T> clazz) {
            super(modid, getItem, settingsItem, clazz);
        }
    }
}
