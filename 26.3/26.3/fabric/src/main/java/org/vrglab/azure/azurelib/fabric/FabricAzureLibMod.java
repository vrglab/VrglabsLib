package org.vrglab.azure.azurelib.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.fabric.platform.FabricAzureLibNetwork;
import org.vrglab.azure.azurelib.network.packet.AzBlockEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.network.packet.AzEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.network.packet.AzItemStackDispatchCommandPacket;

public final class FabricAzureLibMod implements ModInitializer {

    @Override
    public void onInitialize() {
        AzureLib.initialize();
        new FabricAzureLibNetwork();
        PayloadTypeRegistry.clientboundPlay()
            .register(
                AzBlockEntityDispatchCommandPacket.TYPE,
                AzBlockEntityDispatchCommandPacket.CODEC
            );
        PayloadTypeRegistry.clientboundPlay()
            .register(
                AzEntityDispatchCommandPacket.TYPE,
                AzEntityDispatchCommandPacket.CODEC
            );
        PayloadTypeRegistry.clientboundPlay()
            .register(
                AzItemStackDispatchCommandPacket.TYPE,
                AzItemStackDispatchCommandPacket.CODEC
            );
    }
}
