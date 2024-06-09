package org.Vrglab.TestClasses;

import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.VLModInfo;

public class VLScreens {

    public static Object ENTITY_BLOCK_SCREEN_HANDLER = Registry.RegisterScreenHandlerType("entity_block_screen_handler_type", VLModInfo.MOD_ID, TestBlockScreenHandler::new);

    public static void init(){
        Registry.RegisterHandledScreen("entity_block_handled_screen", VLModInfo.MOD_ID, ENTITY_BLOCK_SCREEN_HANDLER, TestBlockScreen::new);
    }
}
