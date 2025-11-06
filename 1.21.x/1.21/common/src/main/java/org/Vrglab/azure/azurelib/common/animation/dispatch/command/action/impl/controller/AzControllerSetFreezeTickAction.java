package org.Vrglab.azure.azurelib.common.animation.dispatch.command.action.impl.controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.common.animation.AzAnimator;
import org.Vrglab.azure.azurelib.common.animation.dispatch.AzDispatchSide;
import org.Vrglab.azure.azurelib.common.animation.dispatch.command.action.AzAction;

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

    public static final ResourceLocation RESOURCE_LOCATION = AzureLib.modResource("controller/set_freeze_tick_offset");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        var controller = animator.getAnimationControllerContainer().getOrNull(controllerName);

        if (controller != null) {
            controller.setAnimationProperties(controller.animationProperties().withFreezeTickOffset(freezeTickOffset));
        }
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return RESOURCE_LOCATION;
    }
}
