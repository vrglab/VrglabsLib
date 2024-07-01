package org.TestSystem.world.Items;

import net.minecraft.item.Item;
import org.TestSystem.TestMod;
import org.Vrglab.AutoRegisteration.Annotations.RegisterItem;
import org.Vrglab.Modloader.Registration.Registry;

import java.util.ArrayList;
import java.util.List;

public class TestItems {

    @RegisterItem(ItemName = "test_item")
    public static Item TEST_ITEM = new Item(TestMod.basicItemSettings());

    public static Object[] getAll(){
        List<Object> objectList = new ArrayList<>();
        objectList.add(TEST_ITEM);
        return objectList.toArray();
    }


    public static void init(){

    }
}
