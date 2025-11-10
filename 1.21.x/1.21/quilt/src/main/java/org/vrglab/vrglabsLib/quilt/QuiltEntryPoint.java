package org.vrglab.vrglabsLib.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;

public class QuiltEntryPoint implements ModInitializer {

    @Override
    public void onInitialize(ModContainer modContainer) {
        VrglabsInitializer.Initialize(Constants.MOD_ID, "");
    }
}
