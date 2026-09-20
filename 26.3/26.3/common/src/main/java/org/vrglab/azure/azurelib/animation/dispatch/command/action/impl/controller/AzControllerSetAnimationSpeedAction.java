package org.vrglab.azure.azurelib.animation.dispatch.command.action.impl.controller;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.animation.AzAnimator;
import org.vrglab.azure.azurelib.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.animation.dispatch.command.action.AzAction;

public record AzControllerSetAnimationSpeedAction(
    String controllerName,
    double animationSpeed
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzControllerSetAnimationSpeedAction> CODEC = StreamCodec.composite(
        ByteBufCodecs.STRING_UTF8,
        AzControllerSetAnimationSpeedAction::controllerName,
        ByteBufCodecs.DOUBLE,
        AzControllerSetAnimationSpeedAction::animationSpeed,
        AzControllerSetAnimationSpeedAction::new
    );

    public static final Identifier RESOURCE_LOCATION = AzureLib.modResource("controller/set_animation_speed");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        var controller = animator.getAnimationControllerContainer().getOrNull(controllerName);

        if (controller != null) {
            controller.setAnimationProperties(controller.animationProperties().withAnimationSpeed(animationSpeed));
        }
    }

    @Override
    public Identifier getIdentifier() {
        return RESOURCE_LOCATION;
    }
}
