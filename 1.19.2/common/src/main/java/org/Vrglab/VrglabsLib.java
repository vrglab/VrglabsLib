package org.Vrglab;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Utils.Modinfo;

public final class VrglabsLib {

    public static Object BLOCK = Registry.RegisterBlock("block", Modinfo.MOD_ID, ()->new Block(AbstractBlock.Settings.of(Material.METAL)), new Item.Settings().group(ItemGroup.DECORATIONS));

    public static Object POI = Registry.RegisterPOI("poi", Modinfo.MOD_ID, BLOCK, 1, 1);
    public static Object PROFESSION = Registry.RegisterProfession("profession", Modinfo.MOD_ID, "poi", new Item[0], new Block[0], null);

    public static void init() {
        Modinfo.LOGGER.info("Starting Vrglab's Lib ...");
        Registry.RegisterVillagerTrade("level1_profession", Modinfo.MOD_ID, PROFESSION, 1, new TradeOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.DIAMOND, 2), 20, 20, 0.02f));
    }
}
