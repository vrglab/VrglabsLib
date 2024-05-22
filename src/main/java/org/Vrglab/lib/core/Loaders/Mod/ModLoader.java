package org.Vrglab.lib.core.Loaders.Mod;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.Vrglab.lib.core.Loaders.Block.BlocksLoader;
import org.Vrglab.lib.core.Loaders.ILoader;
import org.Vrglab.lib.core.Loaders.Item.ItemsLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModLoader {

    private static Map<String, UUID> mods = new HashMap<>();
    private static Map<UUID, ClassLoader> classLoaderMap = new HashMap<>();
    private static Map<UUID, Map<String, DeferredRegister>> registered_mod_registries = new HashMap<>();
    private static Map<UUID, Map<String, String>> registered_mod_packages = new HashMap<>();
    private static Map<UUID, Map<String, ILoader>> registered_mod_loaders = new HashMap<>();

    public static void LoadMod(String ModId, String packages_dir, ClassLoader mod_class_loader, IEventBus mod_eventbus) {
        if(!mods.containsKey(ModId)) {
            UUID id = UUID.randomUUID();
            mods.put(ModId, id);
            classLoaderMap.put(id, mod_class_loader);
            registered_mod_registries.put(id, new HashMap<>());
            registered_mod_packages.put(id, new HashMap<>());
            registered_mod_loaders.put(id, new HashMap<>());

            registered_mod_registries.get(id).put("items", DeferredRegister.create(ForgeRegistries.ITEMS, ModId));
            registered_mod_packages.get(id).put("items", packages_dir + ".items");
            registered_mod_loaders.get(id).put("items", new ItemsLoader());


            registered_mod_registries.get(id).put("blocks", DeferredRegister.create(ForgeRegistries.BLOCKS, ModId));
            registered_mod_packages.get(id).put("blocks", packages_dir + ".blocks");
            registered_mod_loaders.get(id).put("blocks", new BlocksLoader());

            InitializeMod(id);

            registered_mod_registries.get(id).get("items").register(mod_eventbus);
            registered_mod_registries.get(id).get("blocks").register(mod_eventbus);
        }
    }

    public static UUID ModIdNameToUUID(String modid){
        return mods.get(modid);
    }


    public static DeferredRegister<Item> GetRegistry(UUID modid, String wtg) {
       return registered_mod_registries.get(modid).get(wtg);
    }

    public static ClassLoader GetModClassLoader(UUID modid) {
        return classLoaderMap.get(modid);
    }

    public static String GetPackageDir(UUID modid, String wtg) {
        return registered_mod_packages.get(modid).get(wtg);
    }

    public static ILoader GetModLoader(UUID modid, String wtg) {
        return registered_mod_loaders.get(modid).get(wtg);
    }

    public static void InitializeMod(UUID mod) {
        ItemsLoader itemsLoader = (ItemsLoader)GetModLoader(mod, "items");
        itemsLoader.Load(mod);

        BlocksLoader blocksLoader = (BlocksLoader)GetModLoader(mod, "blocks");
        blocksLoader.Load(mod);
    }
}
