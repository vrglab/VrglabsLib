package org.vrglab.vrglabsLib.NeoForge;

import mod.TestMod.TestModeEntry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.NeoForge.Utils.VrglabsNeoForgeInitializer;


@Mod(Constants.MOD_ID)
public class NeoForgeEntry {

    public NeoForgeEntry(IEventBus eventBus) {
        eventBus.addListener(this::setup);
        TestModeEntry.Init();
        VrglabsNeoForgeInitializer.Initialize(eventBus, TestModeEntry.MODID, "mod.TestMod");
    }


    private void setup(final FMLCommonSetupEvent event) {
        VrglabsNeoForgeInitializer.InitializeCommonSetup(event, TestModeEntry.MODID);
    }
}
