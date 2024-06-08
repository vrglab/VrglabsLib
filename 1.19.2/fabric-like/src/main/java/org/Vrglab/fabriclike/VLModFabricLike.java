package org.Vrglab.fabriclike;

import org.Vrglab.Modloader.Types.ICallBackVoidNoArg;
import org.Vrglab.fabriclike.Utils.FabricLikeRegisteryCreator;

public final class VLModFabricLike {
    public static void init(String modid, ICallBackVoidNoArg mod) {
        FabricLikeRegisteryCreator.Create(modid);
        mod.accept();
    }
}
