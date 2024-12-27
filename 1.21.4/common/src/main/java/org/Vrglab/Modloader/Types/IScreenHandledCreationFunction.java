package org.Vrglab.Modloader.Types;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import org.Vrglab.Screen.ScreenHandler;

@FunctionalInterface
public interface IScreenHandledCreationFunction<T extends ScreenHandler, U extends Screen & ScreenHandlerProvider<T>> {

    U create(T handler, PlayerInventory playerInventory, Text title);
}
