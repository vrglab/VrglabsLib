package org.Vrglab.quilt;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.registry.RegistryBuilder;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;

public class VLDataGenerator implements DataGeneratorEntrypoint {
    /**
     * Register {@link DataProvider} with the {@link FabricDataGenerator} during this entrypoint.
     *
     * @param fabricDataGenerator The {@link FabricDataGenerator} instance
     */
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(VLWorldGen::new);
    }

    /**
     * Builds a registry containing dynamic registry entries to be generated.
     * Users should call {@link RegistryBuilder#addRegistry(net.minecraft.registry.RegistryKey, RegistryBuilder.BootstrapFunction)}
     * to register a bootstrap function, which adds registry entries to be generated.
     *
     * <p>This is invoked asynchronously.
     *
     * @param registryBuilder a {@link RegistryBuilder} instance
     */
    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        FabricLikeRegisteryCreator.boostrap(registryBuilder, VLModInfo.MOD_ID);
    }
}
