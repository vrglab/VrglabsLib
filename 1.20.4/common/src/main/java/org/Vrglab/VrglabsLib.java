package org.Vrglab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;

public final class VrglabsLib {


    private static final Object ITEM = Registry.RegisterItem("test", Modinfo.MOD_ID ,()->new Item(new Item.Settings().arch$tab(ItemGroups.TOOLS)));
    public static void init() {
        Modinfo.LOGGER.info("Starting Vrglab's Lib ...");
    }
}
