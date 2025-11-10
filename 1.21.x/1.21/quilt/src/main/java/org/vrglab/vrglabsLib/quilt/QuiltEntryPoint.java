package org.vrglab.vrglabsLib.quilt;

import mod.TestMod.TestModeEntry;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.core.VrglabsInitializer;
import org.vrglab.vrglabsLib.quilt.utils.VrglabsQuiltInitializer;

public class QuiltEntryPoint implements ModInitializer {

    @Override
    public void onInitialize(ModContainer modContainer) {
        TestModeEntry.Init();
        VrglabsQuiltInitializer.Initialize(TestModeEntry.MODID, "mod.TestMod");
    }
}
