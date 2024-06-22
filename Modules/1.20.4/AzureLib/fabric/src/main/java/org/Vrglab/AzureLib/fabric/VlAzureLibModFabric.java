package org.Vrglab.AzureLib.fabric;

import net.fabricmc.api.ModInitializer;

import org.Vrglab.AzureLib.fabriclike.VlAzureLibModFabricLike;

public final class VlAzureLibModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VlAzureLibModFabricLike.init();
    }
}
