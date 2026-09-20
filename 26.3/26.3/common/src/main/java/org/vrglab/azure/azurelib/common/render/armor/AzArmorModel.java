package org.vrglab.azure.azurelib.common.render.armor;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.vrglab.vrglabsLib.Utils.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AzArmorModel<E extends LivingEntity> extends HumanoidModel<E> {

    private final AzArmorRendererPipeline _rendererPipeline;

    public AzArmorModel(AzArmorRendererPipeline rendererPipeline) {
        super(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.PLAYER_INNER_ARMOR));
        this._rendererPipeline = rendererPipeline;
        this.young = false;
    }

    @Override
    public void renderToBuffer(
        @NotNull PoseStack poseStack,
        @Nullable VertexConsumer buffer,
        int packedLight,
        int packedOverlay,
        int color
    ) {
        var mc = Minecraft.getInstance();
        var context = _rendererPipeline.context();
        var currentEntity = context.currentEntity();
        var currentStack = context.currentStack();


        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        RenderBuffers leverRenderBuffer = ReflectionUtil.getField(levelRenderer, "renderBuffers", RenderBuffers.class);

        MultiBufferSource bufferSource = leverRenderBuffer.bufferSource();

        var shouldOutline = Minecraft.getInstance().levelRenderer.shouldShowEntityOutlines() && mc.
            shouldEntityAppearGlowing(
                currentEntity
            );

        if (shouldOutline) {
            bufferSource = leverRenderBuffer.outlineBufferSource();
        }

        var config = _rendererPipeline.config();
        var animatable = context.animatable();
        var partialTick = mc.getTimer().getGameTimeDeltaTicks();
        var textureLocation = config.textureLocation(currentEntity, animatable);
        var renderType = context.getDefaultRenderType(
            animatable,
            textureLocation,
            bufferSource,
            partialTick,
            config.getRenderType(currentEntity, animatable),
            config.alpha(animatable)
        );
        buffer = ItemRenderer.getArmorFoilBuffer(bufferSource, renderType, currentStack.hasFoil());

        var model = _rendererPipeline.renderer().provider().provideBakedModel(currentEntity, animatable);
        _rendererPipeline.render(poseStack, model, animatable, bufferSource, null, buffer, 0, partialTick, packedLight);
    }

    /**
     * Applies settings and transformations pre-render based on the default model
     */
    public void applyBaseModel(HumanoidModel<?> baseModel) {
        this.young = baseModel.young;
        this.crouching = baseModel.crouching;
        this.riding = baseModel.riding;
        this.rightArmPose = baseModel.rightArmPose;
        this.leftArmPose = baseModel.leftArmPose;
    }

    @Override
    public void setAllVisible(boolean pVisible) {
        super.setAllVisible(pVisible);
        var boneContext = _rendererPipeline.context().boneContext();
        boneContext.setAllVisible(pVisible);
    }
}
