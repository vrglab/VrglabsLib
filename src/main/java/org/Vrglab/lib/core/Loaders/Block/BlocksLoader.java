package org.Vrglab.lib.core.Loaders.Block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.lib.core.Database.BlocksDatabase;
import org.Vrglab.lib.core.Database.ItemsDatabase;
import org.Vrglab.lib.core.Loaders.ILoader;
import org.Vrglab.lib.core.Loaders.Item.ItemMarker;
import org.Vrglab.lib.core.Loaders.Mod.ModLoader;
import org.Vrglab.lib.core.Utils.ModLogger;
import org.Vrglab.lib.core.Utils.ReflectionUtils;

import java.util.Set;
import java.util.UUID;

public class BlocksLoader implements ILoader {
    @Override
    public void Load(UUID modid) {
        try {
            Set<Class<?>> class_s = ReflectionUtils.getAnnotatedClassesInPackage(ModLoader.GetPackageDir(modid, "blocks"), BlockMarker.class, ModLoader.GetModClassLoader(modid));
            DeferredRegister registery = ModLoader.GetRegistry(modid, "blocks");
            DeferredRegister items_registery = ModLoader.GetRegistry(modid, "items");
            for (Class<?> clas: class_s) {
                BlockMarker marker = ReflectionUtils.getClassAnnotation(clas, BlockMarker.class);
                RegistryObject registered = registery.register(marker.ItemName(), () -> {
                    return ReflectionUtils.createInstance(clas);
                });
                RegistryObject registered_item =items_registery.register(marker.ItemName(), () -> { return new BlockItem((Block)registered.get(),((org.Vrglab.lib.core.Loaders.Block.IBlockType)registered.get()).GetItemProperties()); });

                ItemsDatabase.getInstance().Store(modid, marker.ItemName(), registered_item);
                BlocksDatabase.getInstance().Store(modid, marker.ItemName(), registered);
            }
        } catch (Throwable t) {
            ModLogger.LOGGER.error(t.getMessage());
            t.printStackTrace();
        }
    }
}
