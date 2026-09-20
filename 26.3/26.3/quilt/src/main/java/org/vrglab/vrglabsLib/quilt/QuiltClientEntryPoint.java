package org.vrglab.vrglabsLib.quilt;

import mod.TestMod.TestModeEntry;
import net.fabricmc.api.ClientModInitializer;
import org.vrglab.vrglabsLib.quilt.utils.VrglabsQuiltInitializer;

public class QuiltClientEntryPoint implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        VrglabsQuiltInitializer.InitializeClient(TestModeEntry.MODID);
    }
}
