package org.Vrglab.AzureLib.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import org.Vrglab.AzureLib.fabriclike.VlAzureLibModFabricLike;

public final class VlAzureLibModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        // Run the Fabric-like setup.
        VlAzureLibModFabricLike.init();
    }
}
