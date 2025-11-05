package org.Vrglab.VrglabsLib.API.FunctionProviders;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface IScreenHandlerTypeCreationFunction<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory playerInventory);
}
