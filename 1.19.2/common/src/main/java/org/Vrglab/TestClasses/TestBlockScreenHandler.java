package org.Vrglab.TestClasses;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.Vrglab.Screen.ScreenHandler;
import org.Vrglab.Screen.ScreenUtils;

public class TestBlockScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public TestBlockScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, new SimpleInventory(3));
    }

    public TestBlockScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super((ScreenHandlerType<?>)VLScreens.ENTITY_BLOCK_SCREEN_HANDLER, syncId);
        checkSize(inventory, 3);
        this.inventory = inventory;
        inventory.onOpen(playerInventory.player);

        this.addSlot(new Slot(inventory, 0, 12, 15));
        this.addSlot(new Slot(inventory, 1, 86, 15));
        this.addSlot(new Slot(inventory, 2, 86, 60));

        ScreenUtils.addPlayerInventory(playerInventory, this);
        ScreenUtils.addPlayerHotbar(playerInventory, this);

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return ScreenUtils.HandleShiftClick(index, this.inventory, this);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
