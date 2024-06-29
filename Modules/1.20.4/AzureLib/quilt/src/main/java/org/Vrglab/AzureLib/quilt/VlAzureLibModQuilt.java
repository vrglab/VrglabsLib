package org.Vrglab.AzureLib.quilt;

import org.Vrglab.AzureLib.VlAzureLibMod;
import org.Vrglab.fabriclike.VLModFabricLike;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import org.Vrglab.AzureLib.fabriclike.VlAzureLibModFabricLike;

public final class VlAzureLibModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        VLModFabricLike.init(VlAzureLibMod.MOD_ID, ()->{
            VlAzureLibModFabricLike.init();
        });
    }
}
