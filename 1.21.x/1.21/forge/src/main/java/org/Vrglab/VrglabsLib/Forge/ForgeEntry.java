package org.Vrglab.VrglabsLib.Forge;

import mod.TestMod.TestModeEntry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.VrglabsLib.Core.Constants;
import org.Vrglab.VrglabsLib.Forge.Utils.VrglabsForgeInitializer;

@Mod(Constants.MOD_ID)
public class ForgeEntry {

    public ForgeEntry() {

        var eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Initialize test mod and your library
        TestModeEntry.Init();
        VrglabsForgeInitializer.Initialize(eventBus, TestModeEntry.MODID, "mod.TestMod");
    }
}
