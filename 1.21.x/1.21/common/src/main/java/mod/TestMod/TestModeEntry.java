package mod.TestMod;

import mod.TestMod.Items.TestItems;
import org.Vrglab.VrglabsLib.API.AutoRegistry.Annotations.RegisterItem;
import org.Vrglab.VrglabsLib.API.AutoRegistry.World.Item;
import org.Vrglab.VrglabsLib.Core.VrglabsInitializer;

public class TestModeEntry {
    public static String MODID = "testmod";


    public static void Init() {
        TestItems.Init();
    }

}
