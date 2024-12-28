package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.item.Item;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.IClampedCallBack;

import java.util.HashMap;
import java.util.function.Supplier;

public class RegistryItem<T extends Item> extends AutoRegisteryObject<T> {
    public RegistryItem(String modid, IClampedCallBack<T> getItem, Supplier<Item.Settings> settingsItem) {
        this.modid = modid;
        this.args = new HashMap<>();
        this.args.put("item", getItem);
        this.args.put("settingsItem", settingsItem);
    }
}
