package org.Vrglab.forge;

import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.targets.FMLClientLaunchHandler;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

@Mod(VLModInfo.MOD_ID)
public final class VLModForge {
    public VLModForge() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), VLModInfo.MOD_ID);
        VrglabsLib.init();
    }

    @Mod.EventBusSubscriber(modid = VLModInfo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ForgeRegistryCreator.CreateClient(VLModInfo.MOD_ID);
        }
    }
}
