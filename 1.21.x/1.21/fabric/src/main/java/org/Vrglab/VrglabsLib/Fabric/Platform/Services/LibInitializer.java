package org.Vrglab.VrglabsLib.Fabric.Platform.Services;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.AzureLibMod;
import org.Vrglab.azure.azurelib.common.config.TestingConfig;
import org.Vrglab.azure.azurelib.common.config.format.ConfigFormats;
import org.Vrglab.azure.azurelib.common.config.io.ConfigIO;
import org.Vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;
import org.Vrglab.azure.azurelib.fabric.platform.FabricAzureLibNetwork;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.Vrglab.VrglabsLib.platform.services.ILibInitializer;

public class LibInitializer implements ILibInitializer {

    @Override
    public void LoadAzureLib(Object... Args) {
        ConfigIO.FILE_WATCH_MANAGER.startService();
        AzureLib.initialize();
        AzureLibMod.initRegistry();
        new FabricAzureLibNetwork();
        AzureLibMod.config = AzureLibMod.registerConfig(TestingConfig.class, ConfigFormats.json()).getConfigInstance();
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> ConfigIO.FILE_WATCH_MANAGER.stopService());
        PayloadTypeRegistry.playS2C()
                .register(
                        AzBlockEntityDispatchCommandPacket.TYPE,
                        AzBlockEntityDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C()
                .register(
                        AzEntityDispatchCommandPacket.TYPE,
                        AzEntityDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C()
                .register(
                        AzItemStackDispatchCommandPacket.TYPE,
                        AzItemStackDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C()
                .register(
                        SendConfigDataPacket.TYPE,
                        SendConfigDataPacket.CODEC
                );
    }
}
