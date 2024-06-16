package org.Vrglab.Modloader.Registration;

import org.Vrglab.Modloader.enumTypes.Geckolib.GeoRegistryTypes;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.VrglabsLib;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.function.Supplier;

public class GeckoLibRegistry extends Registry {

    public static void RegisterGeckoLibArmorRenderer(Supplier<GeoArmorRenderer> supplier, Object item) {
        SimpleRegister(GeoRegistryTypes.ARMOR, VLModInfo.MOD_ID, supplier, item);
    }
}
