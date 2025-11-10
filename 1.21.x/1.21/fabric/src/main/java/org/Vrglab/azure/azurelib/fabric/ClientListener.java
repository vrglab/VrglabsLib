package org.vrglab.azure.azurelib.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import org.vrglab.azure.azurelib.common.network.packet.AzBlockEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzEntityDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.AzItemStackDispatchCommandPacket;
import org.vrglab.azure.azurelib.common.network.packet.SendConfigDataPacket;

public final class ClientListener implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

    }
}
