package org.vrglab.azure.azurelib.world.Armor;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;

import java.util.function.Supplier;

public abstract class AzureArmor extends ArmorItem {


    public AzureArmor(Holder<ArmorMaterial> pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
    }

    public abstract Supplier<? extends AzArmorRenderer> GetAzureRenderer();
}
