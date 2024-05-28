package org.Vrglab.quilt;

import org.Vrglab.VrglabsLib;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;


public final class VLQuiltEntrypoint implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        VrglabsLib.init();
    }
}
