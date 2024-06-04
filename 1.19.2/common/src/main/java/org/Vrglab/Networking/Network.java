package org.Vrglab.Networking;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Networking.Packet.BaseC2SPacket;
import org.Vrglab.Utils.Modinfo;



public class Network {
    public static ICallbackVoid registerGlobalReceiver;
    public static ICallbackVoid clientSendPacket;

    public static void RegisterC2SGlobal(BaseC2SPacket packet_receiver){
        Modinfo.LOGGER.info("Registering C2S: " + packet_receiver.getKey());
        registerGlobalReceiver.accept(packet_receiver.getKey(), new ICallbackVoid() {
            @Override
            public void accept(Object... args) {
               packet_receiver.receive((MinecraftServer)args[0], (ServerPlayerEntity)args[1], (ServerPlayNetworkHandler)args[2], (PacketByteBuf)args[3], args[4]);
            }
        });
    }

    public static void SendC2SPacket(Identifier id, PacketByteBuf buff) {
        clientSendPacket.accept(id, buff);
    }
}
