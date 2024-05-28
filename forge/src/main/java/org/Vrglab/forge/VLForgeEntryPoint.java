package org.Vrglab.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.Vrglab.Modloader.LoaderType;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;


@Mod(Modinfo.MOD_ID)
public final class VLForgeEntryPoint {
    public VLForgeEntryPoint() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(Modinfo.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        VrglabsLib.init();
    }
}
