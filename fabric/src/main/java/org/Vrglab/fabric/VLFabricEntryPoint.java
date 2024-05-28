package org.Vrglab.fabric;

import net.fabricmc.api.ModInitializer;

import org.Vrglab.VrglabsLib;

public final class VLFabricEntryPoint implements ModInitializer {
    @Override
    public void onInitialize() {

        VrglabsLib.init();
    }
}
