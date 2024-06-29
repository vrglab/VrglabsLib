package org.Vrglab.Screen;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class ScreenHandler extends net.minecraft.screen.ScreenHandler {
    protected ScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    @Override
    public boolean insertItem(ItemStack stack, int startIndex, int endIndex, boolean fromLast) {
        return super.insertItem(stack, startIndex, endIndex, fromLast);
    }

    @Override
    public Slot addSlot(Slot slot) {
        return super.addSlot(slot);
    }
}
