package org.Vrglab.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

import java.util.function.Supplier;

@Mod(Modinfo.MOD_ID)
public final class VLForgeEntryPoint {
    public VLForgeEntryPoint() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
