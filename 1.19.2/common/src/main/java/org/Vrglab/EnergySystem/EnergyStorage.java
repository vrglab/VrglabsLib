package org.Vrglab.EnergySystem;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallBackVoidNoArg;
import org.Vrglab.Utils.VLModInfo;

import static org.Vrglab.EnergySystem.EnergyStorageUtils.hasExternalStorage;

public class EnergyStorage implements IEnergyContainer{

    public static <T extends EnergyStorage> T createStorage(Class<T> clazz, long capacity) {
        try {
            return clazz.getConstructor(long.class).newInstance(capacity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create EnergyStorage instance", e);
        }
    }

    public static <T extends EnergyStorage> T createStorage(Class<T> clazz, long capacity, long maxTransfer) {
        try {
            return clazz.getConstructor(long.class, long.class).newInstance(capacity, maxTransfer);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create EnergyStorage instance", e);
        }
    }

    public static <T extends EnergyStorage> T createStorage(Class<T> clazz, long capacity, long maxTransfer, long maxExtract) {
        try {
            return clazz.getConstructor(long.class, long.class, long.class).newInstance(capacity, maxTransfer, maxExtract);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create EnergyStorage instance", e);
        }
    }

    public static <T extends EnergyStorage> T createStorage(Class<T> clazz, long capacity, long maxTransfer, long maxExtract, long energy) {
        try {
            return clazz.getConstructor(long.class, long.class, long.class, long.class).newInstance(capacity, maxTransfer, maxExtract, energy);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create EnergyStorage instance", e);
        }
    }

    public static EnergyStorage createStorage(long capacity){
        return createStorage(EnergyStorage.class, capacity);
    }
    public static EnergyStorage createStorage(long capacity, long maxTransfer){
        return createStorage(EnergyStorage.class, capacity, maxTransfer);
    }

    public static EnergyStorage createStorage(long capacity, long maxTransfer, long maxExtract) {
        return createStorage(EnergyStorage.class, capacity, maxTransfer, maxExtract);
    }

    public static EnergyStorage createStorage(long capacity, long maxTransfer, long maxExtract, long energy){
        return createStorage(EnergyStorage.class, capacity, maxTransfer, maxExtract, energy);
    }

    public static IEnergyContainer getStorageInWorld(World world, BlockPos blockPos, Direction facing){
        IEnergyContainer storage =  null;
        BlockEntity entity = world.getBlockEntity(blockPos.offset(facing));
        if(containEnergyStorage(entity)) {
            try {
                if(entity != null && entity instanceof IEnergySupplier<?>) {
                    storage = ((IEnergySupplier<?>)entity).getEnergyStorage();
                } else if((boolean)hasExternalStorage.accept(entity)){
                    if (EnergyStorageUtils.getCachedContainer(blockPos.offset(facing)) != null) {
                        try{
                            storage = EnergyStorageUtils.getCachedContainer(blockPos.offset(facing));
                            if(((BlockEntity)((EnergyStorage)storage).blockEntity).isRemoved()) {
                                EnergyStorageUtils.removeFromCache(blockPos.offset(facing));
                                throw  new RuntimeException("Accessed Storage is removed");
                            }
                        } catch (Throwable t) {
                            storage = (EnergyStorage)EnergyStorageUtils.wrapExternalStorage.accept(world, blockPos, facing, entity);
                            EnergyStorageUtils.cacheEnergyContainer(blockPos.offset(facing), storage);
                        }
                    } else {
                        storage = (EnergyStorage)EnergyStorageUtils.wrapExternalStorage.accept(world, blockPos, facing, entity);
                        EnergyStorageUtils.cacheEnergyContainer(blockPos.offset(facing), storage);
                    }
                }

            } catch (Throwable t) {

            }
        }
        return storage;
    }

    public static boolean containEnergyStorage(World world, BlockPos blockPos){
        BlockEntity entity = world.getBlockEntity(blockPos);
        return  containEnergyStorage(entity);
    }

    public static boolean containEnergyStorage(BlockEntity entity){
        if(entity != null && entity instanceof IEnergySupplier<?> || (boolean)hasExternalStorage.accept(entity)) {
            try {
                return true;
            } catch (Throwable t) {

            }
        }
        return  false;
    }


    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;
    public ICallBackVoidNoArg makeDirty;
    protected Object actualEnergyInstance, blockEntity;

    public EnergyStorage(long capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public EnergyStorage(long capacity, long maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyStorage(long capacity, long maxReceive, long maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyStorage(long capacity, long maxReceive, long maxExtract, long energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0 , Math.min(capacity, energy));
        this.actualEnergyInstance = EnergyStorageUtils.createStorageInstance.accept(capacity, maxReceive, maxExtract, energy, this, blockEntity);
    }

    public EnergyStorage(Object instance, long capacity, long maxReceive, long maxExtract, long energy) {
        this(capacity, maxReceive, maxExtract, energy);
        this.actualEnergyInstance = instance;
    }

    @Override
    public long receiveEnergy(long maxReceive, boolean simulate) {
        energy += Long.valueOf(EnergyStorageUtils.receiveEnergyInstance.accept(actualEnergyInstance, maxReceive, simulate).toString());
        return energy;
    }

    @Override
    public long extractEnergy(long maxExtract, boolean simulate) {
        energy -= Long.valueOf(EnergyStorageUtils.extractEnergyInstance.accept(actualEnergyInstance, maxExtract, simulate).toString());
        return energy;
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @return Amount of energy that was accepted by the storage.
     */
    @Override
    public long receiveEnergy(long maxReceive) {
        return receiveEnergy(maxReceive, false);
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @return Amount of energy that was (or would have been) extracted from the storage.
     */
    @Override
    public long extractEnergy(long maxExtract) {
        return extractEnergy(maxExtract, false);
    }

    /**
     * Set's the energy Storages block entity type
     * <p> DO NOT USE {@link TypeTransformer#ObjectToType} ON THE GIVEN OBJECT</p>
     * @param blockEntity the un-transformed object of the block entity
     * @return The Energy storage reference
     */
    public EnergyStorage setBlockEntityType(Object blockEntity) {
        this.blockEntity = TypeTransformer.ObjectToType.accept(blockEntity);
        return this;
    }

    public EnergyStorage setMakeDirtyFunction(ICallBackVoidNoArg makeDirty) {
        this.makeDirty = makeDirty;
        return this;
    }

    @Override
    public long getEnergyStored() {
        return energy;
    }

    @Override
    public long getMaxEnergyStored() {
        return capacity;
    }

    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public boolean isEmpty() {
        return (energy == 0);
    }

    public boolean atMaxCapacity(){
        return (energy == capacity);
    }
}
