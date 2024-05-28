package org.Vrglab.quilt;

import dev.architectury.platform.Mod;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.quilt.Utils.QuiltRegistryCreator;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import java.util.function.Supplier;


public final class VLQuiltEntrypoint implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        QuiltRegistryCreator.Create(Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
