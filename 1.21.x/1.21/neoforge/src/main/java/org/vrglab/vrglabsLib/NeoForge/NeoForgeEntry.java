package org.vrglab.vrglabsLib.NeoForge;

import mod.TestMod.TestModeEntry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.NeoForge.Utils.VrglabsNeoForgeInitializer;

@Mod(Constants.MOD_ID)
public class NeoForgeEntry {

    public NeoForgeEntry(IEventBus eventBus) {
        TestModeEntry.Init();
        VrglabsNeoForgeInitializer.Initialize(eventBus, TestModeEntry.MODID, "mod.TestMod");
    }
}
