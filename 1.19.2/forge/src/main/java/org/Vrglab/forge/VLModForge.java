package org.Vrglab.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

@Mod(Modinfo.MOD_ID)
public final class VLModForge {
    public VLModForge() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
