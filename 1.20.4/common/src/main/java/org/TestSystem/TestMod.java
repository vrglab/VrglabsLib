package org.TestSystem;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import org.TestSystem.world.Blocks.TestBlocks;
import org.TestSystem.world.Items.CreativeModeTabs;
import org.TestSystem.world.Items.TestItems;
import org.Vrglab.Utils.Utils;

import java.util.function.Supplier;

public class TestMod {
    public static final String MODID = "vrglabslib";

    public static void init(){
        CreativeModeTabs.init();
        TestItems.init();
        TestBlocks.init();
    }

    public static Item.Settings basicItemSettings() {
        return new Item.Settings();
    }
}
