package org.Vrglab.fabriclike;

import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallbackVoid;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;

public final class VLModFabricLike {
    public static void init(String modid, ICallbackVoid mod) {
        FabricLikeRegisteryCreator.Create(modid);
        mod.accept();
    }
}
