package org.Vrglab.Modloader.Types;

import net.minecraft.entity.player.PlayerInventory;
import org.Vrglab.Screen.ScreenHandler;

@FunctionalInterface
public interface IScreenHandlerTypeCreationFunction<T extends ScreenHandler> {
    T create(int syncId, PlayerInventory playerInventory);
}
