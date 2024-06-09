package org.Vrglab.Screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Identifier;

public class ScreenUtils {
    public static ItemStack HandleShiftClick(int invSlot, Inventory inventory, ScreenHandler handler) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = handler.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < inventory.size()) {
                if (!handler.insertItem(originalStack, inventory.size(), handler.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!handler.insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    public static void drawBG(int width, int height, int backgroundWidth, int backgroundHeight, MatrixStack matrices, HandledScreen screen, Identifier Texture){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, Texture);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        screen.drawTexture(matrices, x, y+2, 0, 0, backgroundWidth, backgroundHeight);
    }

    public static void addPlayerInventory(PlayerInventory playerInventory, ScreenHandler handler) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                handler.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    public static void addPlayerHotbar(PlayerInventory playerInventory, ScreenHandler handler) {
        for (int i = 0; i < 9; ++i) {
            handler.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }
}
