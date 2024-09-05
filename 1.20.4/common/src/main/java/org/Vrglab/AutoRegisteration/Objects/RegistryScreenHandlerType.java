package org.Vrglab.AutoRegisteration.Objects;

import org.Vrglab.Modloader.Types.IScreenHandlerTypeCreationFunction;
import org.Vrglab.Screen.ScreenHandler;

public class RegistryScreenHandlerType<T extends ScreenHandler> extends AutoRegisteryObject<T> {

    public RegistryScreenHandlerType(String modid, IScreenHandlerTypeCreationFunction<T> handler) {
        super(modid);
        this.args.put("handler", handler);
    }
}
