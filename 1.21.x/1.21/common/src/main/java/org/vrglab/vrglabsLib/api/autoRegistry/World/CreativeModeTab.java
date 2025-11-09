package org.vrglab.vrglabsLib.api.autoRegistry.World;

import java.util.HashMap;
import java.util.function.Supplier;

public class CreativeModeTab extends AutoRegistryObject<net.minecraft.world.item.CreativeModeTab> {

    public CreativeModeTab(String modid, Supplier<net.minecraft.world.item.CreativeModeTab> supplier) {
        this.modid = modid;
        this.args = new HashMap<>();
        args.put("supplier", supplier);
    }
}
