package org.TestSystem.world.Items;

import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.AutoRegisteration.Annotations.InitializableClass;
import org.Vrglab.AutoRegisteration.Annotations.RegisterCMT;
import org.Vrglab.AutoRegisteration.Objects.RegistryCMT;
import org.Vrglab.Helpers.CreativeModeTabBuilder;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;

public class CreativeModeTabs {

    @RegisterCMT(Name = "test_tab")
    public static RegistryCMT TEST_GROUP = new RegistryCMT(TestMod.MODID,  CreativeModeTabBuilder.create()
            .setDisplayText("test_tab")
            .setIcon(TestItems.TEST_ITEM.getRawData())
            .setEntries(TestItems.getAll(), TestBlocks.getAll())
            .build());
}
