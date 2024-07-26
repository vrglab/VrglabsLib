package org.TestSystem.world.Items;

import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.Helpers.CreativeModeTabBuilder;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;

public class CreativeModeTabs {

    public static Object TEST_GROUP = Registry.RegisterCreativeModeTab("test_tab", TestMod.MODID,
            CreativeModeTabBuilder.create()
                    .setDisplayText("test_tab")
                    .setIcon(TestItems.TEST_ITEM.getRegisteredObject())
                    .setEntries(TestItems.getAll(), TestBlocks.getAll())
                    .build()
    );

    public static void init(){
    }
}
