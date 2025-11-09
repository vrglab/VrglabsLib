package mod.TestMod.Items;

import net.minecraft.resources.ResourceLocation;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import org.vrglab.vrglabsLib.core.Constants;

public class ExampleArmorRenderer extends AzArmorRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "geo/amethyst_armor.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            Constants.MOD_ID,
            "textures/armor/amethyst_armor.png"
    );

    public ExampleArmorRenderer() {
        super(AzArmorRendererConfig.builder(GEO, TEX).build());
    }
}
