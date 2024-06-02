package org.Vrglab.forge;

import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

import java.util.List;

@Mod(Modinfo.MOD_ID)
public final class VLModForge {
    public VLModForge() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
