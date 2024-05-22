package org.Vrglab.lib.core.Loaders.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.lib.core.Database.ItemsDatabase;
import org.Vrglab.lib.core.Loaders.ILoader;
import org.Vrglab.lib.core.Loaders.Mod.ModLoader;
import org.Vrglab.lib.core.Utils.ModLogger;
import org.Vrglab.lib.core.Utils.ReflectionUtils;
import java.util.Set;
import java.util.UUID;

public class ItemsLoader implements ILoader {

    @Override
    public void Load(UUID modid) {
       try {
           Set<Class<?>> class_s = ReflectionUtils.getAnnotatedClassesInPackage(ModLoader.GetPackageDir(modid, "items"), ItemMarker.class, ModLoader.GetModClassLoader(modid));
           DeferredRegister registery = ModLoader.GetRegistry(modid, "items");
           for (Class<?> clas: class_s) {
               ItemMarker marker = ReflectionUtils.getClassAnnotation(clas, ItemMarker.class);
               RegistryObject registered = registery.register(marker.ItemName(), () -> {
                   return ReflectionUtils.createInstance(clas);
               });
               ItemsDatabase.getInstance().Store(modid, marker.ItemName(), registered);
           }
       } catch (Throwable t) {
           ModLogger.LOGGER.error(t.getMessage());
           t.printStackTrace();
       }
    }
}
