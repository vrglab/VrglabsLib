package org.vrglab.azure.azurelib.neoforge.platform;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

import org.vrglab.azure.azurelib.common.cache.AzureLibCache;
import org.vrglab.azure.azurelib.common.platform.services.AzureLibInitializer;

public class NeoForgeAzureLibInitializer implements AzureLibInitializer {

    @Override
    public void initialize() {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            AzureLibCache.registerReloadListener();
        }
    }
}
