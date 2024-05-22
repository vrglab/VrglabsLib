package org.Vrglab.lib.core.Database;

import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.lib.core.Loaders.Item.Item;

public class ItemsDatabase extends Database<RegistryObject<Item>>{

    private static ItemsDatabase instance;

    private ItemsDatabase() {

    }

    public static ItemsDatabase getInstance() {
        if (instance == null) {
            instance = new ItemsDatabase();
        }
        return instance;
    }
}
