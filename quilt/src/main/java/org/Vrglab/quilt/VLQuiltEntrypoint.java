package org.Vrglab.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.vrglab.fabriclike.VLFabricLikeEntrypoint;

import java.util.function.Supplier;


public final class VLQuiltEntrypoint implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        VLFabricLikeEntrypoint.init();
    }
}
