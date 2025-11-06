package org.vrglab.vrglabsLib.azure.azurelib.forge.platform;

import org.vrglab.azure.azurelib.common.cache.AzureLibCache;
import org.vrglab.azure.azurelib.common.platform.services.AzureLibInitializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;


public class ForgeAzureLibInitializer implements AzureLibInitializer {

    @Override
    public void initialize() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            AzureLibCache.registerReloadListener();
        }
    }
}
