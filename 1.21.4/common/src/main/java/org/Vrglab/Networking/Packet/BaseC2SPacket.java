package org.Vrglab.Networking.Packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class BaseC2SPacket {

    public void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, Object responseSender) {
        ExecuteOnServer(server, player, handler, buf, responseSender);
    }

    protected abstract void ExecuteOnServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, Object responseSender);

    public abstract Identifier getKey();
}
