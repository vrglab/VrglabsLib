package org.Vrglab.AzureLib.fabric;

import net.fabricmc.api.ModInitializer;

import org.Vrglab.AzureLib.VlAzureLibMod;
import org.Vrglab.AzureLib.fabriclike.VlAzureLibModFabricLike;
import org.Vrglab.fabriclike.VLModFabricLike;

public final class VlAzureLibModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VLModFabricLike.init(VlAzureLibMod.MOD_ID, ()->{
            VlAzureLibModFabricLike.init();
        });
    }
}
