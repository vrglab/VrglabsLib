package org.vrglab.azure.azurelib.common.render.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

import org.vrglab.azure.azurelib.common.render.AzRendererPipeline;
import org.vrglab.azure.azurelib.common.render.AzRendererPipelineContext;
import org.vrglab.azure.azurelib.common.render.armor.bone.AzArmorBoneContext;
import org.vrglab.azure.azurelib.core.object.Color;

public class AzArmorRendererPipelineContext extends AzRendererPipelineContext<UUID, ItemStack> {

    private final AzArmorBoneContext _boneContext;

    private HumanoidModel<?> _baseModel;

    private EquipmentSlot _currentSlot;

    private ItemStack _currentStack;

    private boolean _translucent = false;

    public AzArmorRendererPipelineContext(AzRendererPipeline<UUID, ItemStack> rendererPipeline) {
        super(rendererPipeline);
        this._baseModel = null;
        this._boneContext = new AzArmorBoneContext();
        this.currentEntity = null;
        this._currentSlot = null;
        this._currentStack = null;
    }

    @Override
    public RenderType getDefaultRenderType(
        ItemStack animatable,
        ResourceLocation texture,
        @Nullable MultiBufferSource bufferSource,
        float partialTick,
        RenderType defaultRenderType,
        float alpha
    ) {
        return _translucent
            ? RenderType.itemEntityTranslucentCull(texture)
            : defaultRenderType;
    }

    public void prepare(
        @Nullable Entity entity,
        ItemStack stack,
        @Nullable EquipmentSlot slot,
        @Nullable HumanoidModel<?> baseModel
    ) {
        this._baseModel = baseModel;
        this.currentEntity = entity;
        this._currentStack = stack;
        this.animatable = stack;
        this._currentSlot = slot;
    }

    /**
     * Sets whether the rendering pipeline should render with a translucent effect or not.
     *
     * @param translucent A boolean value indicating whether to enable or disable translucency. If true, the rendering
     *                    pipeline will apply a translucent effect to rendered elements. If false, it will render with
     *                    an opaque effect.
     */
    public void setTranslucent(boolean translucent) {
        this._translucent = translucent;
    }

    /**
     * Gets a tint-applying color to render the given animatable with
     * <p>
     * Returns {@link Color#WHITE} by default
     */
    @Override
    public Color getRenderColor(ItemStack animatable, float partialTick, int packedLight) {
        return this._currentStack.is(ItemTags.DYEABLE)
            ? Color.ofOpaque(
                DyedItemColor.getOrDefault(this._currentStack, -6265536)
            )
            : Color.WHITE;
    }

    public HumanoidModel<?> baseModel() {
        return _baseModel;
    }

    public AzArmorBoneContext boneContext() {
        return _boneContext;
    }

    public Entity currentEntity() {
        return currentEntity;
    }

    public EquipmentSlot currentSlot() {
        return _currentSlot;
    }

    public ItemStack currentStack() {
        return _currentStack;
    }
}
