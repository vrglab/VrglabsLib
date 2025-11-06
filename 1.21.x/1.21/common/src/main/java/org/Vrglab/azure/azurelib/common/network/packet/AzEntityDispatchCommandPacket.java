package org.Vrglab.azure.azurelib.common.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

import org.Vrglab.azure.azurelib.common.animation.AzAnimatorAccessor;
import org.Vrglab.azure.azurelib.common.animation.dispatch.AzDispatchSide;
import org.Vrglab.azure.azurelib.common.animation.dispatch.command.AzCommand;
import org.Vrglab.azure.azurelib.common.network.AbstractPacket;
import org.Vrglab.azure.azurelib.common.platform.services.AzureLibNetwork;
import org.Vrglab.azure.azurelib.common.util.client.ClientUtils;

public record AzEntityDispatchCommandPacket(
    int entityId,
    AzCommand dispatchCommand
) implements AbstractPacket {

    public static final Type<AzEntityDispatchCommandPacket> TYPE = new Type<>(
        AzureLibNetwork.AZ_ENTITY_DISPATCH_COMMAND_SYNC_PACKET_ID
    );

    public static final StreamCodec<FriendlyByteBuf, AzEntityDispatchCommandPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT,
        AzEntityDispatchCommandPacket::entityId,
        AzCommand.CODEC,
        AzEntityDispatchCommandPacket::dispatchCommand,
        AzEntityDispatchCommandPacket::new
    );

    public void handle() {
        var entity = ClientUtils.getLevel().getEntity(this.entityId);

        if (entity == null) {
            return;
        }

        var animator = AzAnimatorAccessor.getOrNull(entity);

        if (animator != null) {
            dispatchCommand.actions().forEach(action -> action.handle(AzDispatchSide.SERVER, animator));
        }
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
