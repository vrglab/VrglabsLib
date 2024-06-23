package org.Vrglab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.VLModInfo;

public final class VrglabsLib {

    public static Object ITEM = Registry.RegisterItem("name", VLModInfo.MOD_ID, ()->new Item(new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static Object ITEM2 = Registry.RegisterItem("name2", VLModInfo.MOD_ID, ()->new Item(new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static Object ITEM3 = Registry.RegisterItem("name3", VLModInfo.MOD_ID, ()->new Item(new Item.Settings().group(ItemGroup.DECORATIONS)));

    public static void init() {

    }
}
