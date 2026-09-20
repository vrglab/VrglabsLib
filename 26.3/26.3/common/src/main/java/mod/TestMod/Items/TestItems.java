package mod.TestMod.Items;

import mod.TestMod.TestModeEntry;
import net.minecraft.world.item.*;
import org.vrglab.azure.azurelib.common.render.armor.AzArmorRenderer;
import org.vrglab.azure.azurelib.world.Armor.AzureArmor;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterItem;
import org.vrglab.vrglabsLib.api.autoRegistry.World.Item;

import java.util.function.Supplier;

public class TestItems {

    @RegisterItem(ItemName = "ruby")
    public static Item.SimpleItem RUBY = new Item.SimpleItem(TestModeEntry.MODID,

            (arg)-> new net.minecraft.world.item.Item((net.minecraft.world.item.Item.Properties) arg[0]),
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(16)
    );


    @RegisterItem(ItemName = "ruby_axe")
    public static Item<AxeItem> RUBY_AXE = new Item<>(TestModeEntry.MODID,

            (arg)-> new net.minecraft.world.item.AxeItem(Tiers.DIAMOND, (net.minecraft.world.item.Item.Properties) arg[0]),
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(75).fireResistant()
    );

    @RegisterItem(ItemName = "amethyst_chestplate")
    public static Item<AzureArmor> AMETHYST_CHESTPLATE = new Item<>(TestModeEntry.MODID,

            (arg)-> new AzureArmor(ArmorMaterials.AMETHYST, ArmorItem.Type.CHESTPLATE, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ExampleArmorRenderer::new;
                }
            },
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1),
            AzureArmor.class
    );

    @RegisterItem(ItemName = "amethyst_leggings")
    public static Item<AzureArmor> AMETHYST_LEGGINGS = new Item<>(TestModeEntry.MODID,

            (arg)-> new AzureArmor(ArmorMaterials.AMETHYST, ArmorItem.Type.LEGGINGS, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ExampleArmorRenderer::new;
                }
            },
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1),
            AzureArmor.class
    );

    @RegisterItem(ItemName = "amethyst_helmet")
    public static Item<AzureArmor> AMETHYST_HELMET = new Item<>(TestModeEntry.MODID,

            (arg)-> new AzureArmor(ArmorMaterials.AMETHYST, ArmorItem.Type.HELMET, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ExampleArmorRenderer::new;
                }
            },
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1),
            AzureArmor.class
    );

    @RegisterItem(ItemName = "amethyst_boots")
    public static Item<AzureArmor> AMETHYST_BOOTS = new Item<>(TestModeEntry.MODID,

            (arg)-> new AzureArmor(ArmorMaterials.AMETHYST, ArmorItem.Type.BOOTS, (net.minecraft.world.item.Item.Properties) arg[0]) {

                @Override
                public Supplier<? extends AzArmorRenderer> GetAzureRenderer() {
                    return ExampleArmorRenderer::new;
                }
            },
            () ->  new net.minecraft.world.item.Item.Properties().stacksTo(1),
            AzureArmor.class
    );

    public static void Init() {

    }
}
