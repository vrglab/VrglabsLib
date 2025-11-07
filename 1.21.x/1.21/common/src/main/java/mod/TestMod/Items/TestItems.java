package mod.TestMod.Items;

import mod.TestMod.TestModeEntry;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterItem;
import org.vrglab.vrglabsLib.api.autoRegistry.World.Item;

public class TestItems {

    @RegisterItem(ItemName = "testitem")
    public static Item.SimpleItem ITEM = new Item.SimpleItem(TestModeEntry.MODID,

            (arg)->new net.minecraft.world.item.Item((net.minecraft.world.item.Item.Properties) arg[0])
    ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(16)
    );


    @RegisterItem(ItemName = "fireresistent")
    public static Item.SimpleItem FIRE_RESISTENT = new Item.SimpleItem(TestModeEntry.MODID,

            (arg)->new net.minecraft.world.item.Item((net.minecraft.world.item.Item.Properties) arg[0])
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(75).fireResistant()
    );

    public static void Init() {

    }
}
