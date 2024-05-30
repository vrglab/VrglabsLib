package org.Vrglab.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

@Mod(Modinfo.MOD_ID)
public final class VLModForge {
    public VLModForge() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), Modinfo.MOD_ID);

    }
}
