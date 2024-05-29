package org.Vrglab.fabric;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import org.vrglab.fabriclike.VLFabricLikeEntrypoint;


public final class VLFabricEntryPoint implements ModInitializer {
    @Override
    public void onInitialize() {
        VLFabricLikeEntrypoint.init();
    }
}
