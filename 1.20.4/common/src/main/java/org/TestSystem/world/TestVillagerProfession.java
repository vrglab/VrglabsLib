package org.TestSystem.world;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.AutoRegisteration.Annotations.*;
import org.Vrglab.AutoRegisteration.Objects.*;

public class TestVillagerProfession {

    @RegisterPOI(Name = "test_poi")
    public static RegistryPOI POI = new RegistryPOI(TestMod.MODID, TestBlocks.TEST_POI_BLOCK, 1, 4);

    @RegisterProfession(Name = "test_profession")
    public static RegistryProfession PROFFESSION = new RegistryProfession(TestMod.MODID, POI, new RegistryItem[]{}, new RegistryBlock[]{}, null);

    @RegisterVillagerTrade(Name = "test_trade")
    public static RegistryVillagerTrade TRADE = new RegistryVillagerTrade(TestMod.MODID, PROFFESSION, 1,
            new TradeOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(Items.DIAMOND, 2), 20, 20, 0.02f));
}
