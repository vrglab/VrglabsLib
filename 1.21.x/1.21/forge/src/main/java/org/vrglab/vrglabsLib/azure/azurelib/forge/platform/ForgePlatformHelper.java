package org.vrglab.vrglabsLib.azure.azurelib.forge.platform;

import org.vrglab.azure.azurelib.common.platform.services.IPlatformHelper;
import net.minecraft.core.component.DataComponentType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLLoader;
import org.vrglab.vrglabsLib.Forge.Platform.Services.LibInitializer;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Forge";
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public Path getGameDir() {
        return FMLLoader.getGamePath();
    }

    @Override
    public boolean isServerEnvironment() {
        return FMLEnvironment.dist.isDedicatedServer();
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponent(
        String id,
        UnaryOperator<DataComponentType.Builder<T>> builder
    ) {
        return LibInitializer.DATA_COMPONENTS_REGISTER.register(id, ()->builder.apply(DataComponentType.builder()).build());
    }

    @Override
    public boolean isEnvironmentClient() {
        return FMLEnvironment.dist.isClient();
    }
}
