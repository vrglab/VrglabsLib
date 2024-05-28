package org.Vrglab.forge;

import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

import java.util.function.Supplier;

@Mod(Modinfo.MOD_ID)
public final class VLForgeEntryPoint {
    public VLForgeEntryPoint() {
        ForgeRegistryCreator.Create(FMLJavaModLoadingContext.get().getModEventBus(), Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
