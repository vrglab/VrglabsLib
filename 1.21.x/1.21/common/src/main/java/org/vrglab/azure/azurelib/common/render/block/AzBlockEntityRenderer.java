package org.vrglab.azure.azurelib.common.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.vrglab.azure.azurelib.common.animation.impl.AzBlockAnimator;
import org.vrglab.azure.azurelib.common.render.AzProvider;

/**
 * The {@code AzBlockEntityRenderer} class is an abstract base class for rendering custom block entities. It leverages
 * an animation and rendering pipeline mechanism to provide extended functionalities, such as dynamic animations and
 * model customization.
 *
 * @param <T> The specific type of {@link BlockEntity} that this renderer processes.
 */
public abstract class AzBlockEntityRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {

    private final AzProvider<Long, T> _provider;

    private final AzBlockEntityRendererPipeline<T> _rendererPipeline;

    @Nullable
    private AzBlockAnimator<T> _reusedAzBlockAnimator;

    protected AzBlockEntityRenderer(AzBlockEntityRendererConfig<T> config) {
        this._provider = new AzProvider<>(
            config::createAnimator,
            config::modelLocation,
            blockEntity -> blockEntity.getBlockPos().asLong()
        );
        this._rendererPipeline = createPipeline(config);
    }

    protected AzBlockEntityRendererPipeline<T> createPipeline(AzBlockEntityRendererConfig<T> config) {
        return new AzBlockEntityRendererPipeline<>(config, this);
    }

    @Override
    public void render(
        @NotNull T entity,
        float partialTick,
        @NotNull PoseStack poseStack,
        @NotNull MultiBufferSource source,
        int packedLight,
        int packedOverlay
    ) {
        var cachedEntityAnimator = (AzBlockAnimator<T>) _provider.provideAnimator(
            _rendererPipeline.context().currentEntity(),
            entity
        );
        var model = _provider.provideBakedModel(_rendererPipeline.context().currentEntity(), entity);

        // Point the renderer's current animator reference to the cached entity animator before rendering.
        _reusedAzBlockAnimator = cachedEntityAnimator;

        // Execute the render pipeline.
        _rendererPipeline.render(poseStack, model, entity, source, null, null, 0, partialTick, packedLight);
    }

    public AzBlockAnimator<T> getAnimator() {
        return _reusedAzBlockAnimator;
    }
}
