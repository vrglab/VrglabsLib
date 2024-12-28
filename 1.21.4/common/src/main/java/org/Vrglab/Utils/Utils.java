package org.Vrglab.Utils;

import net.minecraft.block.AbstractBlock;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.enumTypes.IRegistryType;
import static org.Vrglab.Modloader.enumTypes.RegistryTypes.BLOCK;

public class Utils {

    public static <T> T convertToMcSafeType(Object registry_result){
        return (T)TypeTransformer.ObjectToType.accept(registry_result);
    }

    public static Item.Settings MakeSafeSettings(Item.Settings settings, IRegistryType registery, Identifier id){
        switch (registery){
            case BLOCK -> {
                return settings.useBlockPrefixedTranslationKey().registryKey(RegistryKey.of(RegistryKey.ofRegistry(Registries.ITEM.getDefaultId()), id));
            }
            default -> {
                return settings.useItemPrefixedTranslationKey().registryKey(RegistryKey.of(RegistryKey.ofRegistry(Registries.ITEM.getDefaultId()), id));
            }
        }
    }

    public static AbstractBlock.Settings MakeSafeSettings(AbstractBlock.Settings settings, IRegistryType registery, Identifier id){
        return settings.registryKey(RegistryKey.of(RegistryKey.ofRegistry(Registries.BLOCK.getDefaultId()), id));
    }
}
