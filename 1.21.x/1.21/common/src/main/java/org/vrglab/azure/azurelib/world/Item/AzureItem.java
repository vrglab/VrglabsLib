package org.vrglab.azure.azurelib.world.Item;

import net.minecraft.world.item.Item;
import org.vrglab.azure.azurelib.common.render.item.AzItemRenderer;

import java.util.function.Supplier;

public abstract class AzureItem extends Item {

    public AzureItem(Properties pProperties) {
        super(pProperties);
    }

    public abstract Supplier<? extends AzItemRenderer> GetAzureRenderer();
}
