package org.vrglab.vrglabsLib.quilt.Platform.Services;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.quiltmc.loader.api.QuiltLoader;
import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.vrglabsLib.platform.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class PlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Quilt";
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return QuiltLoader.isDevelopmentEnvironment();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return QuiltLoader.isModLoaded(modId);
    }

    @Override
    public Path getGameDir() {
        return QuiltLoader.getGameDir();
    }

    @Override
    public boolean isServerEnvironment() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER;
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponent(
            String id,
            UnaryOperator<DataComponentType.Builder<T>> builder
    ) {
        final DataComponentType<T> componentType = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                AzureLib.modResource(id).toString(),
                builder.apply(DataComponentType.builder()).build()
        );

        return () -> componentType;
    }

    @Override
    public boolean isEnvironmentClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }
}
