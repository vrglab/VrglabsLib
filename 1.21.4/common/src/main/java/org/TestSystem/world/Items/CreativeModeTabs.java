package org.TestSystem.world.Items;

import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.AutoRegisteration.Annotations.InitializableClass;
import org.Vrglab.Helpers.CreativeModeTabBuilder;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;

@InitializableClass
public class CreativeModeTabs {

    public static Object TEST_GROUP = Registry.RegisterCreativeModeTab("test_tab", TestMod.MODID,
            CreativeModeTabBuilder.create()
                    .setDisplayText("test_tab")
                    .setIcon(TestItems.TEST_ITEM.getRawData())
                    .setEntries(TestItems.getAll(), TestBlocks.getAll())
                    .build()
    );

    public static void init(){
    }
}
