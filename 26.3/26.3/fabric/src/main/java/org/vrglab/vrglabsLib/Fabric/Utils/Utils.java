package org.vrglab.vrglabsLib.Fabric.Utils;

import net.minecraft.resources.Identifier;

public class Utils extends org.vrglab.vrglabsLib.Utils.Utils {

    public static Identifier CreateNewId(String modid, String pathId ){
        return Identifier.fromNamespaceAndPath(modid, pathId);
    }
}
