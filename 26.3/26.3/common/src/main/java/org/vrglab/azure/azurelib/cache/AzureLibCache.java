/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.cache;

import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.vrglab.azure.azurelib.animation.cache.AzBakedAnimationCache;
import org.vrglab.azure.azurelib.model.cache.AzBakedModelCache;

public final class AzureLibCache implements PreparableReloadListener {

    @Override
    public @NonNull CompletableFuture<Void> reload(
        SharedState sharedState,
        @NonNull Executor prepExecutor,
        PreparationBarrier preparationBarrier,
        @NonNull Executor applicationExecutor
    ) {
        final ResourceManager resourceManager = sharedState.resourceManager();

        return CompletableFuture
            .allOf(
                AzBakedAnimationCache.getInstance().loadAnimations(prepExecutor, resourceManager),
                AzBakedModelCache.getInstance().loadModels(prepExecutor, resourceManager)
            )
            .thenCompose(preparationBarrier::wait)
            .thenAcceptAsync(empty -> {}, applicationExecutor);
    }
}
