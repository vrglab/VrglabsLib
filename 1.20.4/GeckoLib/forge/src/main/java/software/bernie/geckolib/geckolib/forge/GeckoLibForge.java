package software.bernie.geckolib.geckolib.forge;

import dev.architectury.platform.forge.EventBuses;
import software.bernie.geckolib.geckolib.GeckoLib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GeckoLib.MOD_ID)
public class GeckoLibForge {
    public GeckoLibForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(GeckoLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        GeckoLib.init();
    }
}