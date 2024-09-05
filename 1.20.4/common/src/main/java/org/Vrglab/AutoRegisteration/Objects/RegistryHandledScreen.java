package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import org.Vrglab.Modloader.Types.IScreenHandledCreationFunction;
import org.Vrglab.Screen.ScreenHandler;

public class RegistryHandledScreen<T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<T>> extends AutoRegisteryObject {

    public RegistryHandledScreen(String modid, RegistryScreenHandlerType<T> handlerType, IScreenHandledCreationFunction<T, U> handledCreationFunction) {
        super(modid);
        this.args.put("handlerType", handlerType);
        this.args.put("handledCreationFunction", handledCreationFunction);
    }
}
