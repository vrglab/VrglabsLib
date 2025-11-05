package org.Vrglab.VrglabsLib.API.FunctionProviders;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;


@FunctionalInterface
public interface IScreenHandledCreationFunction<T extends AbstractContainerMenu, U extends AbstractContainerScreen<T>> {

    U create(T handler, Inventory playerInventory, TitleScreen title);
}
