package mod.TestMod.Items;

import mod.TestMod.TestModeEntry;
import mod.TestMod.block.TestBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.vrglab.azure.azurelib.core.math.Constant;
import org.vrglab.vrglabsLib.api.autoRegistry.Annotations.RegisterCreativeModeTab;
import org.vrglab.vrglabsLib.api.autoRegistry.World.CreativeModeTab;
import org.vrglab.vrglabsLib.core.Constants;

public class TestCreativeModeTabs {

    @RegisterCreativeModeTab(Name = "test_tab_items")
    public static CreativeModeTab CREATIVE_TAB_ITEMS = new  CreativeModeTab(TestModeEntry.MODID,
            ()->net.minecraft.world.item.CreativeModeTab
                    .builder(net.minecraft.world.item.CreativeModeTab.Row.TOP, 16)
                    .icon(()->new ItemStack(TestItems.RUBY.getRegisteredObject()))
                    .title(Component.translatable("itemGroup.testmod.creative_tab_items"))
                    .displayItems(
                            (itemDisplayParameters, output) ->{
                                output.accept(TestItems.RUBY.getRegisteredObject());
                                output.accept(TestItems.RUBY_AXE.getRegisteredObject());
                                output.accept(TestItems.ARMOR_BODY.getRegisteredObject());
                                output.accept(TestItems.ARMOR_LEGS.getRegisteredObject());
                                output.accept(TestItems.ARMOR_HEAD.getRegisteredObject());
                                output.accept(TestItems.ARMOR_BOOTS.getRegisteredObject());
                            }
                    )
                    .build());

    @RegisterCreativeModeTab(Name = "test_tab_blocks")
    public static CreativeModeTab CREATIVE_TAB_BLOCKS = new  CreativeModeTab(TestModeEntry.MODID,
            ()->net.minecraft.world.item.CreativeModeTab
                    .builder(net.minecraft.world.item.CreativeModeTab.Row.TOP, 16)
                    .icon(()->new ItemStack((Block) TestBlocks.RUBY_BLOCK.getRegisteredObject()))
                    .title(Component.translatable("creative_tab_blocks"))
                    .displayItems(
                            (itemDisplayParameters, output) ->{
                                output.accept((Block) TestBlocks.RUBY_BLOCK.getRegisteredObject());
                            }
                    )
                    .build());

    public static void Init() {

    }
}
