package mod.TestMod.Items;

import mod.TestMod.TestModeEntry;
import mod.TestMod.block.TestBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterCreativeModeTab;
import org.vrglab.vrglabsLib.api.autoRegistry.World.CreativeModeTab;

public class TestCreativeModeTabs {

    @RegisterCreativeModeTab(Name = "test_tab_items")
    public static CreativeModeTab CREATIVE_TAB_ITEMS = new  CreativeModeTab(TestModeEntry.MODID,
            ()-> net.minecraft.world.item.CreativeModeTab.
                    builder(net.minecraft.world.item.CreativeModeTab.Row.TOP, 16).
                    icon(()-> new ItemStack(TestItems.RUBY.getRegisteredObject())).
                    title(Component.translatable("itemGroup.testmod.creative_tab_items")).
                    displayItems(
                            (itemDisplayParameters, output) -> {
                                output.accept(TestItems.RUBY.getRegisteredObject());
                                output.accept(TestItems.RUBY_AXE.getRegisteredObject());
                                output.accept(TestItems.AMETHYST_CHESTPLATE.getRegisteredObject());
                                output.accept(TestItems.AMETHYST_BOOTS.getRegisteredObject());
                                output.accept(TestItems.AMETHYST_LEGGINGS.getRegisteredObject());
                                output.accept(TestItems.AMETHYST_HELMET.getRegisteredObject());
                            }
                    ).
                    build());

    @RegisterCreativeModeTab(Name = "test_tab_blocks")
    public static CreativeModeTab CREATIVE_TAB_BLOCKS = new  CreativeModeTab(TestModeEntry.MODID,
            ()-> net.minecraft.world.item.CreativeModeTab.
                    builder(net.minecraft.world.item.CreativeModeTab.Row.TOP, 16).
                    icon(()-> new ItemStack((Block) TestBlocks.RUBY_BLOCK.getRegisteredObject())).
                    title(Component.translatable("creative_tab_blocks")).
                    displayItems(
                            (itemDisplayParameters, output) -> {
                                output.accept((Block) TestBlocks.RUBY_BLOCK.getRegisteredObject());
                            }
                    ).
                    build());

    public static void Init() {

    }
}
