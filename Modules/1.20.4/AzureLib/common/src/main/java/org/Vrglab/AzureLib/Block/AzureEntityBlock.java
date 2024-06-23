package org.Vrglab.AzureLib.Block;

import mod.azure.azurelib.common.api.common.animatable.GeoBlockEntity;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.Vrglab.Modloader.Types.ICallBack;

import java.util.List;

public abstract class AzureEntityBlock extends BaseEntityBlock implements GeoBlockEntity {
    protected final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    protected AzureEntityBlock(Properties properties) {
        super(properties);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        List<AnimationController> controllerList = (List<AnimationController>)getControllers().accept(controllers);
        for (AnimationController controller: controllerList) {
            controllers.add(controller);
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
       return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /* ABSTRACT FUNCTIONS */
    public abstract ICallBack getControllers();
}
