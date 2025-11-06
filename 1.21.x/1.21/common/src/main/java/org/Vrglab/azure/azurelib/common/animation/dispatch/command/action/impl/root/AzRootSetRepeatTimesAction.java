package org.Vrglab.azure.azurelib.common.animation.dispatch.command.action.impl.root;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.common.animation.AzAnimator;
import org.Vrglab.azure.azurelib.common.animation.dispatch.AzDispatchSide;
import org.Vrglab.azure.azurelib.common.animation.dispatch.command.action.AzAction;

public record AzRootSetRepeatTimesAction(
    double repeatXTimes
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzRootSetRepeatTimesAction> CODEC = StreamCodec.composite(
        ByteBufCodecs.DOUBLE,
        AzRootSetRepeatTimesAction::repeatXTimes,
        AzRootSetRepeatTimesAction::new
    );

    public static final ResourceLocation RESOURCE_LOCATION = AzureLib.modResource("root/set_repeat_times_tick_offset");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        animator.getAnimationControllerContainer()
            .getAll()
            .forEach(
                controller -> controller.setAnimationProperties(
                    controller.animationProperties().withRepeatXTimes(repeatXTimes)
                )
            );
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return RESOURCE_LOCATION;
    }
}
