package org.vrglab.azure.azurelib.quilt.platform;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.common.cache.AzureLibCache;
import org.vrglab.azure.azurelib.common.platform.services.AzureLibInitializer;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FabricAzureLibInitializer implements AzureLibInitializer {

    @Override
    public void initialize() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES)
            .registerReloadListener(new IdentifiableResourceReloadListener() {

                @Override
                public ResourceLocation getFabricId() {
                    return AzureLib.modResource("models");
                }

                @Override
                public @NotNull CompletableFuture<Void> reload(
                    PreparationBarrier synchronizer,
                    ResourceManager manager,
                    ProfilerFiller prepareProfiler,
                    ProfilerFiller applyProfiler,
                    Executor prepareExecutor,
                    Executor applyExecutor
                ) {
                    return AzureLibCache.reload(
                        synchronizer,
                        manager,
                        prepareProfiler,
                        applyProfiler,
                        prepareExecutor,
                        applyExecutor
                    );
                }
            });
    }
}
