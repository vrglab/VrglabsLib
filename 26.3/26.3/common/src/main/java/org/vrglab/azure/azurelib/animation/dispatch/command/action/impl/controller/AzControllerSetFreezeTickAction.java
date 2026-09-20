package org.vrglab.azure.azurelib.animation.dispatch.command.action.impl.controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.animation.AzAnimator;
import org.vrglab.azure.azurelib.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.animation.dispatch.command.action.AzAction;

public record AzControllerSetFreezeTickAction(
    String controllerName,
    double freezeTickOffset
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzControllerSetFreezeTickAction> CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        AzControllerSetFreezeTickAction::controllerName,
        ByteBufCodecs.DOUBLE,
        AzControllerSetFreezeTickAction::freezeTickOffset,
        AzControllerSetFreezeTickAction::new
    );

    public static final Identifier RESOURCE_LOCATION = AzureLib.modResource("controller/set_freeze_tick_offset");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        var controller = animator.getAnimationControllerContainer().getOrNull(controllerName);

        if (controller != null) {
            controller.setAnimationProperties(controller.animationProperties().withFreezeTickOffset(freezeTickOffset));
        }
    }

    @Override
    public Identifier getIdentifier() {
        return RESOURCE_LOCATION;
    }
}
