package org.vrglab.azure.azurelib.fabric;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.AzureLibMod;
import org.vrglab.azure.azurelib.common.config.TestingConfig;
import org.vrglab.azure.azurelib.common.config.format.ConfigFormats;
import org.vrglab.azure.azurelib.common.config.io.ConfigIO;
import org.vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;
import org.vrglab.azure.azurelib.fabric.platform.FabricAzureLibNetwork;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public class AzureLibFabricInitializer {

    public static void Initialize() {
        ConfigIO.FILE_WATCH_MANAGER.startService();
        AzureLib.initialize();
        AzureLibMod.initRegistry();
        new FabricAzureLibNetwork();
        AzureLibMod.config = AzureLibMod.registerConfig(TestingConfig.class, ConfigFormats.json()).getConfigInstance();
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> ConfigIO.FILE_WATCH_MANAGER.stopService());
        PayloadTypeRegistry.playS2C().
                register(
                        AzBlockEntityDispatchCommandPacket.TYPE,
                        AzBlockEntityDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C().
                register(
                        AzEntityDispatchCommandPacket.TYPE,
                        AzEntityDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C().
                register(
                        AzItemStackDispatchCommandPacket.TYPE,
                        AzItemStackDispatchCommandPacket.CODEC
                );
        PayloadTypeRegistry.playS2C().
                register(
                        SendConfigDataPacket.TYPE,
                        SendConfigDataPacket.CODEC
                );
    }
}
