package org.vrglab.fabriclike;


import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.vrglab.fabriclike.Utils.FabricLikeRegistryCreator;
public final class VLFabricLikeEntrypoint {
    public static void init() {
        FabricLikeRegistryCreator.Create(Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
