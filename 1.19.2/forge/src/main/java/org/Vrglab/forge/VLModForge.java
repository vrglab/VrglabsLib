package org.Vrglab.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

@Mod(VLModInfo.MOD_ID)
public final class VLModForge {
    public VLModForge() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), VLModInfo.MOD_ID);
        VrglabsLib.init();
    }
}
