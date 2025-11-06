package org.vrglab.azure.azurelib.common.animation.dispatch.command.action.impl.root;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.common.animation.AzAnimator;
import org.vrglab.azure.azurelib.common.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.common.animation.dispatch.command.action.AzAction;

public record AzRootSetReverseAction(
    boolean hasReverse
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzRootSetReverseAction> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL,
        AzRootSetReverseAction::hasReverse,
        AzRootSetReverseAction::new
    );

    public static final ResourceLocation RESOURCE_LOCATION = AzureLib.modResource(
        "root/set_reverse_tick_offset"
    );

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        animator.getAnimationControllerContainer()
            .getAll()
            .forEach(
                controller -> controller.setAnimationProperties(
                    controller.animationProperties().withShouldReverse(hasReverse)
                )
            );
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return RESOURCE_LOCATION;
    }
}
