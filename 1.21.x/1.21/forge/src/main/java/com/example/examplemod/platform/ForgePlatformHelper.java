package com.example.examplemod.platform;

import net.minecraft.core.component.DataComponentType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLLoader;
import org.Vrglab.VrglabsLib.platform.services.IPlatformHelper;

import java.nio.file.Path;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ForgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {

        return "Forge";
    }

    @Override
    public boolean isModLoaded(String modId) {

        return ModList.get().isLoaded(modId);
    }

    @Override
    public Path getGameDir() {
        return null;
    }

    @Override
    public boolean isServerEnvironment() {
        return false;
    }

    @Override
    public boolean isEnvironmentClient() {
        return false;
    }

    @Override
    public <T> Supplier<DataComponentType<T>> registerDataComponent(String id, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return null;
    }

    @Override
    public boolean isDevelopmentEnvironment() {

        return !FMLLoader.isProduction();
    }
}