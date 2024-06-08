package org.Vrglab.neoforge;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.neoforge.Utils.NeoForgeRegistryCreator;

@Mod(Modinfo.MOD_ID)
public final class VLModNeoForge {
    public VLModNeoForge() {
        NeoForgeRegistryCreator.Create(ModLoadingContext.get().getActiveContainer().getEventBus(), Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
