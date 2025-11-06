package org.Vrglab.VrglabsLib.azure.azurelib.forge.platform;


import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.common.network.AbstractPacket;
import org.Vrglab.azure.azurelib.common.platform.services.AzureLibNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.*;

import java.util.HashMap;
import java.util.Map;

public class ForgeAzureLibNetwork implements AzureLibNetwork {
    private static final int PROTOCOL_VERSION = 1;

    private static SimpleChannel INSTANCE = ChannelBuilder
            .named(ResourceLocation.fromNamespaceAndPath(AzureLib.MOD_ID, "networking"))
            .networkProtocolVersion(PROTOCOL_VERSION)
            .clientAcceptedVersions((status, version) -> {
                    return version == PROTOCOL_VERSION;
            })
            .serverAcceptedVersions((status, version) -> {
                return version == PROTOCOL_VERSION;
            }).simpleChannel()
            ;

    private static int ID = 0;


    private static int id() {
        return ID++;
    }

    private static final Map<ResourceLocation, StreamCodec<FriendlyByteBuf, ? extends AbstractPacket>> codecRegistry = new HashMap<>();



    @Override
    public <B extends FriendlyByteBuf, P extends AbstractPacket> void registerPacketInternal(CustomPacketPayload.Type<P> payloadType, StreamCodec<B, P> codec, boolean isClientBound) {

        codecRegistry.put(payloadType.id(), (StreamCodec<FriendlyByteBuf, ? extends AbstractPacket>) codec);


        INSTANCE.messageBuilder(DynamicPacket.class, id())
                .encoder((packet, buf) -> {
                    buf.writeResourceLocation(packet.typeId);
                    StreamCodec<FriendlyByteBuf, AbstractPacket> c = (StreamCodec<FriendlyByteBuf, AbstractPacket>) codecRegistry.get(packet.typeId);
                    c.encode(buf, packet.payload);
                })
                .decoder(buf -> {
                    ResourceLocation id = buf.readResourceLocation();
                    StreamCodec<FriendlyByteBuf, AbstractPacket> c = (StreamCodec<FriendlyByteBuf, AbstractPacket>) codecRegistry.get(id);
                    if (c == null)
                        throw new RuntimeException("No codec registered for " + id);
                    AbstractPacket p = c.decode(buf);
                    return new DynamicPacket(id, p);
                })
                .consumer((packet, ctx) -> {
                    ctx.enqueueWork(() -> packet.payload.handle());
                    ctx.setPacketHandled(true);
                })
                .add();
    }

    @Override
    public void sendToTrackingEntityAndSelf(AbstractPacket packet, Entity entityToTrack) {
        DynamicPacket wrapper = new DynamicPacket(packet.type().id(), packet);
        INSTANCE.send(wrapper, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(entityToTrack));
    }

    @Override
    public void sendToEntitiesTrackingChunk(AbstractPacket packet, ServerLevel level, BlockPos blockPos) {
        DynamicPacket wrapper = new DynamicPacket(packet.type().id(), packet);

        ChunkPos pos = new ChunkPos(blockPos);
        LevelChunk chunk = level.getChunk(pos.x, pos.z);

        if (chunk != null) {
            INSTANCE.send(wrapper, PacketDistributor.TRACKING_CHUNK.with(chunk));
        }
    }

    @Override
    public void sendClientPacket(ServerPlayer player, String id) {

    }

    @Override
    public void sendToPlayer(AbstractPacket packet, ServerPlayer player) {
        DynamicPacket wrapper = new DynamicPacket(packet.type().id(), packet);
        INSTANCE.send(wrapper, PacketDistributor.PLAYER.with(player));
    }

    public static class DynamicPacket {
        public final ResourceLocation typeId;
        public final AbstractPacket payload;

        public DynamicPacket(ResourceLocation typeId, AbstractPacket payload) {
            this.typeId = typeId;
            this.payload = payload;
        }
    }
}
