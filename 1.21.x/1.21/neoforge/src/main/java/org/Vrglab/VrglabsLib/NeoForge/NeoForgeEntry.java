package org.Vrglab.VrglabsLib.NeoForge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.Vrglab.VrglabsLib.Core.Constants;
import org.Vrglab.VrglabsLib.NeoForge.Utils.VrglabsNeoForgeInitializer;

@Mod(Constants.MOD_ID)
public class NeoForgeEntry {

    public NeoForgeEntry(IEventBus eventBus) {
        VrglabsNeoForgeInitializer.Initialize(eventBus, Constants.MOD_ID, "examplemod");
    }
}
