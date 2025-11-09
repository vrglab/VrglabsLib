package mod.TestMod.Items;

import mod.TestMod.TestModeEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.item.ArmorMaterials;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRendererConfig;
import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterItem;
import org.vrglab.vrglabsLib.api.autoRegistry.World.Item;
import org.vrglab.vrglabsLib.core.Constants;

import java.util.function.Supplier;

public class TestItems {

    @RegisterItem(ItemName = "ruby")
    public static Item.SimpleItem RUBY = new Item.SimpleItem(TestModeEntry.MODID,

            (arg)->new net.minecraft.world.item.Item((net.minecraft.world.item.Item.Properties) arg[0])
    ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(16)
    );


    @RegisterItem(ItemName = "ruby_axe")
    public static Item<AxeItem> RUBY_AXE = new Item(TestModeEntry.MODID,

            (arg)->new net.minecraft.world.item.AxeItem(Tiers.DIAMOND,(net.minecraft.world.item.Item.Properties) arg[0])
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(75).fireResistant()
    );

    @RegisterItem(ItemName = "armor_body")
    public static Item<AzureArmor> ARMOR_BODY = new Item(TestModeEntry.MODID,

            (arg)->new AzureArmor(mod.TestMod.Items.ArmorMaterials.IRON, ArmorItem.Type.CHESTPLATE, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ()->new ExampleArmorRenderer();
                }
            }
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1)
    );

    @RegisterItem(ItemName = "armor_legs")
    public static Item<AzureArmor> ARMOR_LEGS = new Item(TestModeEntry.MODID,

            (arg)->new AzureArmor(ArmorMaterials.GOLD, ArmorItem.Type.LEGGINGS, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ()->new ExampleArmorRenderer();
                }
            }
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1)
    );

    @RegisterItem(ItemName = "armor_head")
    public static Item<AzureArmor> ARMOR_HEAD = new Item(TestModeEntry.MODID,

            (arg)->new AzureArmor(ArmorMaterials.GOLD, ArmorItem.Type.HELMET, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ()->new ExampleArmorRenderer();
                }
            }
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1)
    );

    @RegisterItem(ItemName = "armor_boots")
    public static Item<AzureArmor> ARMOR_BOOTS = new Item(TestModeEntry.MODID,

            (arg)->new AzureArmor(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ()->new ExampleArmorRenderer();
                }
            }
            ,
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1)
    );

    public static void Init() {

    }
}


