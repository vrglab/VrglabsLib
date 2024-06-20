package software.bernie.geckolib.geckolib.fabric;

import software.bernie.geckolib.geckolib.GeckoLib;
import net.fabricmc.api.ModInitializer;

public class GeckoLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        GeckoLib.init();
    }
}