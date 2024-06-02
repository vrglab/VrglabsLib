package org.Vrglab;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.gen.GenerationStep;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.VinillaBiomeTypes;
import org.Vrglab.Utils.Modinfo;

public class VlPoiAndProfession {
    public static Object POI = Registry.RegisterPOI("poi", Modinfo.MOD_ID, VlBlocks.BLOCK, 1, 1);
    public static Object PROFESSION = Registry.RegisterProfession("profession", Modinfo.MOD_ID, "poi", new Item[0], new Block[0], null);

    public static void init(){
        Registry.RegisterVillagerTrade("level1_profession", Modinfo.MOD_ID, PROFESSION, 1, new TradeOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.DIAMOND, 2), 20, 20, 0.02f));
    }
}
