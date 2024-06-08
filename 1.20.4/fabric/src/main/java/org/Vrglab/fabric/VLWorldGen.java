package org.Vrglab.fabric;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryWrapper;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;

import java.util.concurrent.CompletableFuture;

public class VLWorldGen extends FabricDynamicRegistryProvider {


    public VLWorldGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        FabricLikeRegisteryCreator.configureBootstrappables(registries, entries);
    }

    @Override
    public String getName() {
        return "World Gen";
    }
}
