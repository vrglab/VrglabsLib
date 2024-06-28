package org.TestSystem.world.Items;

import net.minecraft.item.Item;
import org.TestSystem.TestMod;
import org.Vrglab.Modloader.Registration.Registry;

public class TestItems {

    public static Object TEST_ITEM = Registry.RegisterItem("test_item", TestMod.MODID, ()->new Item(TestMod.basicItemSettings()));


    public static void init(){

    }
}
