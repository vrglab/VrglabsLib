package org.vrglab.vrglabsLib.api.functionProviders;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

@FunctionalInterface
public interface IScreenHandlerTypeCreationFunction<T extends AbstractContainerMenu> {
    T create(int syncId, Inventory playerInventory);
}
