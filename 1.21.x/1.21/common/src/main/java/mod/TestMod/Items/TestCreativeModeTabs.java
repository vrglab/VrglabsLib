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
                    .icon(()->new ItemStack(TestItems.ITEM.getRegisteredObject()))
                    .title(Component.translatable("test_tab_items"))
                    .displayItems(
                            (itemDisplayParameters, output) ->{
                                output.accept(TestItems.ITEM.getRegisteredObject());
                                output.accept(TestItems.FIRE_RESISTENT.getRegisteredObject());
                            }
                    )
                    .build());

    @RegisterCreativeModeTab(Name = "test_tab_blocks")
    public static CreativeModeTab CREATIVE_TAB_BLOCKS = new  CreativeModeTab(TestModeEntry.MODID,
            ()->net.minecraft.world.item.CreativeModeTab
                    .builder(net.minecraft.world.item.CreativeModeTab.Row.TOP, 16)
                    .icon(()->new ItemStack((Block) TestBlocks.TEST_BLOCK.getRegisteredObject()))
                    .title(Component.translatable("test_tab"))
                    .displayItems(
                            (itemDisplayParameters, output) ->{
                                output.accept((Block) TestBlocks.TEST_BLOCK.getRegisteredObject());
                            }
                    )
                    .build());

    public static void Init() {

    }
}
