package org.Vrglab.quilt.Utils;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;

import java.util.function.Supplier;

@Deprecated(forRemoval = true)
public class QuiltRegistryCreator {
    public static void Create(String modid) {
        ICallBack ItemRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.ITEM, new Identifier(args[2].toString(), args[0].toString()), ((Supplier<Item>)args[1]).get());
            }
        };
        ICallBack BlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                Block b = net.minecraft.registry.Registry.register(Registries.BLOCK, new Identifier(args[2].toString(), args[0].toString()), ((Supplier<Block>)args[1]).get());
                net.minecraft.registry.Registry.register(Registries.ITEM, new Identifier(args[2].toString(), args[0].toString()), new BlockItem(b, (Item.Settings) args[3]));
                return b;
            }
        };
        ICallBack ItemlessBlockRegistryCallBack = new ICallBack() {
            @Override
            public Object accept(Object... args) {
                return net.minecraft.registry.Registry.register(Registries.BLOCK, new Identifier(args[2].toString(), args[0].toString()), ((Supplier<Block>)args[1]).get());
            }
        };

        Registry.initRegistry(ItemRegistryCallBack, RegistryTypes.ITEM, modid);
        Registry.initRegistry(ItemlessBlockRegistryCallBack, RegistryTypes.ITEMLESS_BLOCK, modid);
        Registry.initRegistry(BlockRegistryCallBack, RegistryTypes.BLOCK, modid);
    }
}
