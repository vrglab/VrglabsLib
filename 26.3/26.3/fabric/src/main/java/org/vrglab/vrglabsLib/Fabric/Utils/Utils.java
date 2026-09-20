package org.vrglab.vrglabsLib.Fabric.Utils;

import net.minecraft.resources.ResourceLocation;

public class Utils extends org.vrglab.vrglabsLib.Utils.Utils {

    public static ResourceLocation CreateNewId(String modid, String pathId ){
        return ResourceLocation.fromNamespaceAndPath(modid, pathId);
    }
}
