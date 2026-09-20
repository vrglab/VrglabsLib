package org.vrglab.azure.azurelib.animation.dispatch.command.action.impl.controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.animation.AzAnimator;
import org.vrglab.azure.azurelib.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.animation.dispatch.command.action.AzAction;
import org.vrglab.azure.azurelib.animation.easing.AzEasingType;

public record AzControllerSetEasingTypeAction(
    String controllerName,
    AzEasingType easingType
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzControllerSetEasingTypeAction> CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        AzControllerSetEasingTypeAction::controllerName,
        AzEasingType.STREAM_CODEC,
        AzControllerSetEasingTypeAction::easingType,
        AzControllerSetEasingTypeAction::new
    );

    public static final Identifier RESOURCE_LOCATION = AzureLib.modResource("controller/set_easing_type");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        var controller = animator.getAnimationControllerContainer().getOrNull(controllerName);

        if (controller != null) {
            controller.setAnimationProperties(controller.animationProperties().withEasingType(easingType));
        }
    }

    @Override
    public Identifier getIdentifier() {
        return RESOURCE_LOCATION;
    }
}
