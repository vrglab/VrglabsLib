package org.Vrglab.neoforge;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.neoforge.Utils.NeoForgeRegistryCreator;

@Mod(VLModInfo.MOD_ID)
public final class VLModNeoForge {
    public VLModNeoForge() {
        NeoForgeRegistryCreator.Create(ModLoadingContext.get().getActiveContainer().getEventBus(), VLModInfo.MOD_ID);
        VrglabsLib.init();
    }
}
