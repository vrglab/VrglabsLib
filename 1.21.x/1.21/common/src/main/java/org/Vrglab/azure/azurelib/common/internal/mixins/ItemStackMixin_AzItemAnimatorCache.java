package org.Vrglab.azure.azurelib.common.internal.mixins;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.common.animation.AzAnimator;
import org.Vrglab.azure.azurelib.common.animation.AzAnimatorAccessor;
import org.Vrglab.azure.azurelib.common.animation.cache.AzIdentifiableItemStackAnimatorCache;
import org.Vrglab.azure.azurelib.common.animation.impl.AzItemAnimator;
import org.Vrglab.azure.azurelib.common.util.AzureLibUtil;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin_AzItemAnimatorCache implements AzAnimatorAccessor<UUID, ItemStack> {

    @Override
    public void setAnimator(@Nullable AzAnimator<UUID, ItemStack> animator) {
        var itemStack = AzureLibUtil.<ItemStack>self(this);
        AzIdentifiableItemStackAnimatorCache.getInstance().add(itemStack, (AzItemAnimator) animator);
    }

    @Override
    public @Nullable AzAnimator<UUID, ItemStack> getAnimatorOrNull() {
        var self = AzureLibUtil.<ItemStack>self(this);
        var uuid = self.getComponents().get(AzureLib.AZ_ID.get());
        return AzIdentifiableItemStackAnimatorCache.getInstance().getOrNull(uuid);
    }
}
