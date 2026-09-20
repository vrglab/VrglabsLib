package org.vrglab.azure.azurelib.animation.dispatch.command.action.impl.root;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.animation.AzAnimator;
import org.vrglab.azure.azurelib.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.animation.dispatch.command.action.AzAction;
import org.vrglab.azure.azurelib.animation.easing.AzEasingType;

/**
 * The AzRootSetEasingTypeAction class represents an action within the AzureLib animation system that modifies the
 * easing type used in the animation properties for all animation controllers within an {@link AzAnimator}.
 */
public record AzRootSetEasingTypeAction(
    AzEasingType easingType
) implements AzAction {

    public static final StreamCodec<FriendlyByteBuf, AzRootSetEasingTypeAction> CODEC = StreamCodec.composite(
        AzEasingType.STREAM_CODEC,
        AzRootSetEasingTypeAction::easingType,
        AzRootSetEasingTypeAction::new
    );

    public static final Identifier RESOURCE_LOCATION = AzureLib.modResource("root/set_easing_type");

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        animator.getAnimationControllerContainer()
            .getAll()
            .forEach(
                controller -> controller.setAnimationProperties(
                    controller.animationProperties().withEasingType(easingType)
                )
            );
    }

    @Override
    public Identifier getIdentifier() {
        return RESOURCE_LOCATION;
    }
}
