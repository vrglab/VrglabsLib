package org.Vrglab;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.Vrglab.Networking.Packet.BaseC2SPacket;
import org.Vrglab.Utils.VLModInfo;

public class TestPacket extends BaseC2SPacket {
    @Override
    protected void ExecuteOnServer(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, Object responseSender) {
        VLModInfo.LOGGER.info("Packet Sent");
    }

    @Override
    public Identifier getKey() {
        return new Identifier(VLModInfo.MOD_ID, "net_test_packet");
    }
}
