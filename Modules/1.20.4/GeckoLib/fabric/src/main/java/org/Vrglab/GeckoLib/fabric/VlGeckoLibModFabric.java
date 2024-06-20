package org.Vrglab.GeckoLib.fabric;

import net.fabricmc.api.ModInitializer;

import org.Vrglab.GeckoLib.fabriclike.VlGeckoLibModFabricLike;

public final class VlGeckoLibModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VlGeckoLibModFabricLike.init();
    }
}
