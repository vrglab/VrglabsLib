package org.Vrglab.lib.core;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.Vrglab.lib.core.Loaders.Mod.ModLoader;
import org.Vrglab.lib.core.Utils.ModInfo;

import org.Vrglab.lib.core.Utils.ModLogger;
import org.slf4j.Logger;

@Mod(ModInfo.MODID)
public class VrglabsLibMain {

    public VrglabsLibMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register for server starting event
        MinecraftForge.EVENT_BUS.register(this);
    }
}
