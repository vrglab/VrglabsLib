package org.Vrglab.TestClasses;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.Vrglab.Helpers.ImplementedInventory;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Utils.VLModInfo;
import org.jetbrains.annotations.Nullable;

public class TestBlockEntity extends BlockEntity implements ImplementedInventory{

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    public TestBlockEntity( BlockPos pos, BlockState state) {
        super((BlockEntityType<?>)TypeTransformer.ObjectToType.accept(VlBlocks.BLOCK_ENTITY_TYPE), pos, state);
    }

    /**
     * Gets the item list of this inventory.
     * Must return the same instance every time it's called.
     *
     * @return the item list
     */
    @Override
    public DefaultedList<ItemStack> getItems() {
        return this.inventory;
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, BlockEntity blockEntity) {
        VLModInfo.LOGGER.info("Block entity Type tick");
    }
}
