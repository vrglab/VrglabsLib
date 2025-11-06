package org.Vrglab.VrglabsLib.Fabric;

import mod.TestMod.TestModeEntry;
import net.fabricmc.api.ModInitializer;
import org.Vrglab.VrglabsLib.Fabric.Utils.VrglabsFabricInitializer;

public class FabricEntryPoint implements ModInitializer {

    @Override
    public void onInitialize() {
        TestModeEntry.Init();
        VrglabsFabricInitializer.Initialize(TestModeEntry.MODID, "mod.TestMod");
    }
}
