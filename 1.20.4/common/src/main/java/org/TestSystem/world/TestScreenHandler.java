package org.TestSystem.world;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import org.TestSystem.TestMod;
import org.Vrglab.AutoRegisteration.Annotations.RegisterHandledScreen;
import org.Vrglab.AutoRegisteration.Annotations.RegisterScreenHandlerType;
import org.Vrglab.AutoRegisteration.Objects.RegistryHandledScreen;
import org.Vrglab.AutoRegisteration.Objects.RegistryScreenHandlerType;

public class TestScreenHandler {

    @RegisterScreenHandlerType(Name = "test_handler")
    public static RegistryScreenHandlerType HANDLER_TYPE = new RegistryScreenHandlerType(TestMod.MODID, null);

    @RegisterHandledScreen(Name = "test_handled_screen")
    public static RegistryHandledScreen HANDLED = new RegistryHandledScreen(TestMod.MODID, HANDLER_TYPE, null);
}
