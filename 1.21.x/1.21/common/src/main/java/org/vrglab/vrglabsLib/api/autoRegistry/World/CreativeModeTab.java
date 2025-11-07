package org.vrglab.vrglabsLib.api.autoRegistry.World;

import java.util.function.Supplier;

public class CreativeModeTab extends AutoRegistryObject<net.minecraft.world.item.CreativeModeTab> {

    public CreativeModeTab(String modid, Supplier<net.minecraft.world.item.CreativeModeTab> supplier) {
        this.modid = modid;
        this.supplier = supplier;
    }
}
