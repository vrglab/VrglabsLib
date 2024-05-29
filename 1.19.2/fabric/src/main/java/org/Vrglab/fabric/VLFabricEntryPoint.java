package org.Vrglab.fabric;

import net.fabricmc.api.ModInitializer;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;
import org.Vrglab.VrglabsLib;
import org.Vrglab.fabric.Utils.FabricRegistryCreator;

import java.util.function.Supplier;

public final class VLFabricEntryPoint implements ModInitializer {
    @Override
    public void onInitialize() {
        FabricRegistryCreator.Create(Modinfo.MOD_ID);
        VrglabsLib.init();
    }
}
