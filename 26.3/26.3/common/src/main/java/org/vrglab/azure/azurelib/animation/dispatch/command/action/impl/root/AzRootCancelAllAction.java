package org.vrglab.azure.azurelib.animation.dispatch.command.action.impl.root;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.animation.AzAnimator;
import org.vrglab.azure.azurelib.animation.dispatch.AzDispatchSide;
import org.vrglab.azure.azurelib.animation.dispatch.command.action.AzAction;

/**
 * The AzRootCancelAllAction class is a predefined implementation of the {@link AzAction} interface that cancels all
 * animations on all controllers of an {@link AzAnimator}. This action is effectively used to reset the animation state
 * by nullifying the current animation on all active controllers.
 */
public class AzRootCancelAllAction implements AzAction {

    public static final AzRootCancelAllAction INSTANCE = new AzRootCancelAllAction();

    public static final StreamCodec<FriendlyByteBuf, AzRootCancelAllAction> CODEC = StreamCodec.unit(INSTANCE);

    public static final Identifier RESOURCE_LOCATION = AzureLib.modResource("root/cancel_all");

    private AzRootCancelAllAction() {}

    @Override
    public void handle(AzDispatchSide originSide, AzAnimator<?, ?> animator) {
        var controllerContainer = animator.getAnimationControllerContainer();
        var controllers = controllerContainer.getAll();

        controllers.forEach(controller -> controller.setCurrentAnimation(null));
    }

    @Override
    public Identifier getIdentifier() {
        return RESOURCE_LOCATION;
    }
}
