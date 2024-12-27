package org.Vrglab.Helpers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.Vrglab.Utils.Utils;

public class CreativeModeTabBuilder {
    private ItemGroup.Builder builder;

    public static CreativeModeTabBuilder create(){
        var instance = new CreativeModeTabBuilder();
        instance.builder = ItemGroup.create(null, -1);
        return instance;
    }

    public CreativeModeTabBuilder setDisplayText(String txt) {
        builder.displayName(Text.translatable(txt));
        return this;
    }

    public CreativeModeTabBuilder setIcon(Object item) {
        builder.icon(()->new ItemStack((Item)Utils.convertToMcSafeType(item)));
        return this;
    }

    public CreativeModeTabBuilder setEntries(Object[] itementeries, Object[] blockenteries) {
        builder.entries(((displayContext, entries1) -> {
            for (var data: itementeries) {
                entries1.add((Item)Utils.convertToMcSafeType(data));
            }
            for (var data: blockenteries) {
                entries1.add((Block)Utils.convertToMcSafeType(data));
            }
        }));
        return this;
    }

    public ItemGroup build(){
        return this.builder.build();
    }
}
