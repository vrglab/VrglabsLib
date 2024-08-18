package org.TestSystem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import org.TestSystem.world.Blocks.TestBlocks;
import org.TestSystem.world.Items.CreativeModeTabs;
import org.TestSystem.world.Items.TestItems;
import org.Vrglab.AutoRegisteration.AutoRegistryLoader;
import org.Vrglab.Utils.Utils;

import java.util.function.Supplier;

public class TestMod {
    public static final String MODID = "vrglabslib";

    public static void init(){
        AutoRegistryLoader.LoadAllInPackage("org.TestSystem", TestMod.MODID);
    }

    public static Item.Settings basicItemSettings() {
        return new Item.Settings().arch$tab(ItemGroups.BUILDING_BLOCKS);
    }
}
