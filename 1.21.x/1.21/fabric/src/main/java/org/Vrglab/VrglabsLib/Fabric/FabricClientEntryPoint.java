package org.vrglab.vrglabsLib.Fabric;

import mod.TestMod.TestModeEntry;
import net.fabricmc.api.ClientModInitializer;
import org.vrglab.vrglabsLib.Fabric.Utils.VrglabsFabricInitializer;

import java.util.function.Supplier;

public class FabricClientEntryPoint implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        VrglabsFabricInitializer.InitializeClient(TestModeEntry.MODID);
    }
}
