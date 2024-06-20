package org.Vrglab.GeckoLib.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import org.Vrglab.GeckoLib.fabriclike.VlGeckoLibModFabricLike;

public final class VlGeckoLibModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        // Run the Fabric-like setup.
        VlGeckoLibModFabricLike.init();
    }
}
