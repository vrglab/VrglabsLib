package org.Vrglab.AzureLib.Item;

import mod.azure.azurelib.common.api.client.model.GeoModel;
import mod.azure.azurelib.common.api.client.renderer.GeoItemRenderer;
import mod.azure.azurelib.common.api.common.animatable.GeoItem;
import mod.azure.azurelib.common.internal.client.RenderProvider;
import mod.azure.azurelib.common.internal.common.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.common.internal.common.core.animation.AnimatableManager;
import mod.azure.azurelib.common.internal.common.core.animation.AnimationController;
import mod.azure.azurelib.common.internal.common.util.AzureLibUtil;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.Item;
import org.Vrglab.Modloader.Types.ICallBack;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class AzureItem extends Item implements GeoItem {
    protected final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);
    private final Supplier<Object> renderProvider = GeoItem.makeRenderer(this);
    public AzureItem(Properties properties) {
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
    public void createRenderer(Consumer<Object> consumer) {
        consumer.accept(new RenderProvider() {
            private AzureItem.Renderer renderer;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null)
                    renderer = new AzureItem.Renderer<>(getModel().get());
                return this.renderer;
            }
        });
    }

    @Override
    public Supplier<Object> getRenderProvider() {
        return renderProvider;
    }


    /* ABSTRACT FUNCTIONS */
    public abstract ICallBack getControllers();
    public abstract Supplier<GeoModel<? extends AzureItem>> getModel();


    /* SUB CLASSES */

    public class Renderer<T extends AzureItem> extends GeoItemRenderer<T> {
        public Renderer(GeoModel<T> model) {
            super(model);
        }
    }
}
