package org.TestSystem.world.Items;

import net.minecraft.item.Item;
import org.TestSystem.TestMod;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;
import java.util.List;

public class TestItems {

    public static Object TEST_ITEM = Registry.RegisterItem("test_item", TestMod.MODID, ()->new Item(TestMod.basicItemSettings()));


    public static Object[] getAll(){
        List<Object> objectList = new ArrayList<>();
        objectList.add(TEST_ITEM);
        return objectList.toArray();
    }


    public static void init(){

    }
}
