package org.vrglab.vrglabsLib.Utils;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.api.helpers.TypeTransformer;
import org.vrglab.vrglabsLib.api.registries.interfaces.IRegistryType;

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
