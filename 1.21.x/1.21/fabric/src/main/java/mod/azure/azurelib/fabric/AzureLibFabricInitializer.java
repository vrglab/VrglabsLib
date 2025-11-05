package mod.azure.azurelib.fabric;

import mod.azure.azurelib.AzureLib;
import mod.azure.azurelib.AzureLibMod;
import mod.azure.azurelib.common.config.TestingConfig;
import mod.azure.azurelib.common.config.format.ConfigFormats;
import mod.azure.azurelib.common.config.io.ConfigIO;
import mod.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import mod.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import mod.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import mod.azure.azurelib.common.network.packet.SendConfigDataPacket;
import mod.azure.azurelib.common.platform.Services;
import mod.azure.azurelib.fabric.platform.FabricAzureLibNetwork;
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
