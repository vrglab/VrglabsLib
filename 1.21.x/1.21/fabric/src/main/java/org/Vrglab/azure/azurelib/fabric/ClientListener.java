package org.Vrglab.azure.azurelib.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import org.Vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.Vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;

public final class ClientListener implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(
            AzEntityDispatchCommandPacket.TYPE,
            (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
            AzItemStackDispatchCommandPacket.TYPE,
            (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
            AzBlockEntityDispatchCommandPacket.TYPE,
            (packet, context) -> packet.handle()
        );
        ClientPlayNetworking.registerGlobalReceiver(
            SendConfigDataPacket.TYPE,
            (packet, context) -> packet.handle()
        );
    }
}
