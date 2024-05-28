package org.Vrglab.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.VrglabsLib;


@Mod(VrglabsLib.MOD_ID)
public final class VLForgeEntryPoint {
    public VLForgeEntryPoint() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(VrglabsLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());


        VrglabsLib.init();
    }
}
