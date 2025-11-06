package org.vrglab.vrglabsLib.Forge;

import mod.TestMod.TestModeEntry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.Forge.Utils.VrglabsForgeInitializer;

@Mod(Constants.MOD_ID)
public class ForgeEntry {

    public ForgeEntry() {

        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Initialize test mod and your library
        TestModeEntry.Init();
        VrglabsForgeInitializer.Initialize(eventBus, TestModeEntry.MODID, "mod.TestMod");
    }
}
