package org.Vrglab.lib.core.Database;

import net.minecraftforge.registries.RegistryObject;
import org.Vrglab.lib.core.Loaders.Block.Block;

public class BlocksDatabase extends Database<RegistryObject<Block>> {

    private static BlocksDatabase instance;

    private BlocksDatabase() {

    }

    public static BlocksDatabase getInstance() {
        if (instance == null) {
            instance = new BlocksDatabase();
        }
        return instance;
    }
}
