package org.Vrglab.VrglabsLib.Utils;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.Vrglab.VrglabsLib.API.Helpers.TypeTransformer;
import org.Vrglab.VrglabsLib.API.Registries.IRegistryType;

import static org.Vrglab.VrglabsLib.API.Registries.RegistryTypes.BLOCK;

public class Utils {

    public static <T> T convertToMcSafeType(Object registry_result){
        return (T) TypeTransformer.ObjectToType.accept(registry_result);
    }

    public static Item.Properties MakeSafeSettings(Item.Properties settings, IRegistryType registery, ResourceLocation id){
        return settings;
    }

    public static BlockBehaviour.Properties MakeSafeSettings(BlockBehaviour.Properties settings, IRegistryType registery, ResourceLocation id){
        return settings;
    }
}
