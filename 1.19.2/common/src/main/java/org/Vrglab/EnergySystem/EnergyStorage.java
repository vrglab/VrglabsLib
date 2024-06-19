package org.Vrglab.EnergySystem;

import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.Types.ICallBackVoidNoArg;

public class EnergyStorage implements IEnergyContainer{

    public static ICallBack createStorageInstance, extractEnergyInstance, receiveEnergyInstance;

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
        this.actualEnergyInstance = createStorageInstance.accept(capacity, maxReceive, maxExtract, energy, this, blockEntity);
    }

    @Override
    public long receiveEnergy(long maxReceive, boolean simulate) {
        energy = (long) receiveEnergyInstance.accept(actualEnergyInstance, maxReceive, simulate);
        return energy;
    }

    @Override
    public long extractEnergy(long maxExtract, boolean simulate) {
        energy = (long) extractEnergyInstance.accept(actualEnergyInstance, maxExtract, simulate);
        return energy;
    }

    public EnergyStorage setBlockEntityType(Object blockEntity) {
        this.blockEntity = blockEntity;
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
}
