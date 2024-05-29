package org.Vrglab;

import net.minecraft.item.Item;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;

public final class VrglabsLib {
    private static final Object ITEM = Registry.RegisterItem("test", Modinfo.MOD_ID ,()->new Item(new Item.Settings()));

    public static void init() {
        Modinfo.LOGGER.info("Starting Vrglab's Lib ...");
    }
}
