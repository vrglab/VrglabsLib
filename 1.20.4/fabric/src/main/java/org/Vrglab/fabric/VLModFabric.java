package org.Vrglab.fabric;

import net.fabricmc.api.ModInitializer;

import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.fabriclike.VLModFabricLike;

public final class VLModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        VLModFabricLike.init(Modinfo.MOD_ID, ()-> VrglabsLib.init());
    }
}
