package org.Vrglab.TestClasses;

import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.VLModInfo;

public class VlRecipes {

    public static Object SERIALIZER = Registry.RegisterRecipeSerializer(TestRecipeType.Serializer.ID, VLModInfo.MOD_ID, TestRecipeType.Serializer.INSTANCE);
    public static Object TYPE =  Registry.RegisterRecipeType(TestRecipeType.Type.ID, VLModInfo.MOD_ID, TestRecipeType.Type.INSTANCE);

    public static void init(){

    }
}
