package org.Vrglab.quilt;

import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import org.Vrglab.fabriclike.VLModFabricLike;

public final class VLModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        VLModFabricLike.init(Modinfo.MOD_ID, (args)-> VrglabsLib.init());
    }
}
