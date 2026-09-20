package mod.TestMod;

import mod.TestMod.Items.TestCreativeModeTabs;
import mod.TestMod.Items.TestItems;
import mod.TestMod.block.TestBlocks;

public class TestModeEntry {
    public static String MODID = "testmod";


    public static void Init() {
        TestItems.Init();
        TestBlocks.Init();
        TestCreativeModeTabs.Init();
    }

}
