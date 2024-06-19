package org.Vrglab.TestClasses;

import dev.architectury.registry.menu.ExtendedMenuProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.Vrglab.EnergySystem.EnergyStorage;
import org.Vrglab.EnergySystem.IEnergySupplier;
import org.Vrglab.Helpers.ImplementedInventory;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Utils.VLModInfo;
import org.jetbrains.annotations.Nullable;

public class TestBlockEntity extends BlockEntity implements ImplementedInventory, NamedScreenHandlerFactory, IEnergySupplier<EnergyStorage> {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(3, ItemStack.EMPTY);

    private final EnergyStorage energy_storage = EnergyStorage.createStorage(5000).setBlockEntityType(VlBlocks.BLOCK_ENTITY_TYPE).setMakeDirtyFunction(()->markDirty());


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
        if(blockEntity instanceof TestBlockEntity) {
            TestBlockEntity entity = (TestBlockEntity)  blockEntity;

           if(EnergyStorage.containEnergyStorage(world, blockPos.offset(Direction.EAST))) {
               entity.energy_storage.receiveEnergy(1);
               VLModInfo.LOGGER.info("Current status: " + entity.energy_storage.getEnergyStored());
           } else if(EnergyStorage.containEnergyStorage(world, blockPos.offset(Direction.NORTH))) {
               EnergyStorage storage = EnergyStorage.getStorageInWorld(world, blockPos, Direction.NORTH);
               if(!entity.energy_storage.isEmpty() && !storage.atMaxCapacity()) {
                   storage.receiveEnergy(1);
                   entity.energy_storage.extractEnergy(1);
                   VLModInfo.LOGGER.info("Storage: " + storage.getEnergyStored() + " Sender: " + entity.energy_storage.getEnergyStored());
               }
           }
        }
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block_entity_menu_name");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, inventory);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        Inventories.readNbt(nbt, inventory);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new TestBlockScreenHandler(syncId, inv, this);
    }

    @Override
    public EnergyStorage getEnergyStorage() {
        return energy_storage;
    }
}
