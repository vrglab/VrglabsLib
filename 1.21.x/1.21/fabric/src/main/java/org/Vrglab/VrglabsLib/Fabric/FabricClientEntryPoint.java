package org.vrglab.vrglabsLib.Fabric;

import mod.TestMod.Items.TestItems;
import net.fabricmc.api.ClientModInitializer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRendererRegistry;

import java.util.function.Supplier;

public class FabricClientEntryPoint implements ClientModInitializer {
    /**
     * Runs the mod initializer on the client environment.
     */
    @Override
    public void onInitializeClient() {
        AzArmorRendererRegistry.register((Supplier<AzArmorRenderer>) TestItems.ARMOR_BODY.getRegisteredObject().GetAzureRenderer(),
                TestItems.ARMOR_BODY.getRegisteredObject(),
                TestItems.ARMOR_BOOTS.getRegisteredObject(),
                TestItems.ARMOR_LEGS.getRegisteredObject(),
                TestItems.ARMOR_HEAD.getRegisteredObject()
        );
    }
}
