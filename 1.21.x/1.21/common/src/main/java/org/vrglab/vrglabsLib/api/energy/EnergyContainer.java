package org.vrglab.vrglabsLib.api.energy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.vrglab.vrglabsLib.api.callbacks.ICallBackVoidNoArg;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergyContainer;
import org.vrglab.vrglabsLib.api.energy.interfaces.IEnergySupplier;
import org.vrglab.vrglabsLib.platform.Services;
public class EnergyContainer implements IEnergyContainer {

    protected long energy;
    protected long capacity;
    protected long maxReceive;
    protected long maxExtract;

    private final Object _rawLoaderDependentContainer;
    private Object _rawBlockEntity;
    private ICallBackVoidNoArg _makeDirty;

    public EnergyContainer(long capacity) {
        this(capacity, capacity, capacity, 0);
    }

    public EnergyContainer(long capacity, long maxTransfer) {
        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public EnergyContainer(long capacity, long maxReceive, long maxExtract) {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public EnergyContainer(long capacity, long maxReceive, long maxExtract, long energy) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0 , Math.min(capacity, energy));
        this._rawLoaderDependentContainer = Services.ENERGY.CreateContainerInstance(capacity, maxReceive, maxExtract, energy, this, _rawBlockEntity);
    }

    private EnergyContainer(long capacity, long maxReceive, long maxExtract, long energy, Object rawLoaderDependentContainer, Object blockEntity) {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
        this.energy = Math.max(0 , Math.min(capacity, energy));
        this._rawLoaderDependentContainer = rawLoaderDependentContainer;
        _rawBlockEntity =  blockEntity;
    }

    public Object getRawBlockEntity() {
        return _rawBlockEntity;
    }

    public <T> T GetModloaderContainer(Class<T> tClass) {
        if (tClass.isAssignableFrom(_rawLoaderDependentContainer.getClass())) {
            return tClass.cast(_rawLoaderDependentContainer);
        }
        throw new RuntimeException("Requested Energy Container Type does not match " + Services.PLATFORM.getPlatformName() + "'s Energy Container Type ");
    }

    public EnergyContainer setMakeDirtyFunction(ICallBackVoidNoArg makeDirty) {
        this._makeDirty = makeDirty;
        return this;
    }

    public static boolean pushEnergyTo(BlockEntity self, Level world, BlockPos blockPos, Direction dir, long amnt) {
        if (EnergyController.containEnergyStorage(world, blockPos.offset(dir.getNormal()))) {
            EnergyContainer storage = (EnergyContainer) EnergyController.getStorageInWorld(world, blockPos, dir);
            EnergyContainer self_storage = (EnergyContainer) ((IEnergySupplier<?>) self).getEnergyStorage();
            if (storage != null && (!self_storage.isEmpty() && !storage.atMaxCapacity())) {
                storage.receiveEnergy(amnt);
                self_storage.extractEnergy(amnt);
                return true;
            }
        }
        return false;
    }

    public static boolean pullEnergyFrom(BlockEntity self, Level world, BlockPos blockPos, Direction dir, long amnt) {
        if (EnergyController.containEnergyStorage(world, blockPos.offset(dir.getNormal()))) {
            EnergyContainer storage = (EnergyContainer) EnergyController.getStorageInWorld(world, blockPos, dir);
            EnergyContainer self_storage = (EnergyContainer) ((IEnergySupplier<?>) self).getEnergyStorage();
            if (storage != null && (!self_storage.atMaxCapacity() && !storage.isEmpty())) {
                storage.extractEnergy(amnt);
                self_storage.receiveEnergy(amnt);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive Maximum amount of energy to be inserted.
     * @param simulate   If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    @Override
    public long receiveEnergy(long maxReceive, boolean simulate) {
        energy += Services.ENERGY.GiveEnergyToContainer(_rawLoaderDependentContainer, maxReceive, simulate);
        return energy;
    }

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract Maximum amount of energy to be extracted.
     * @param simulate   If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    @Override
    public long extractEnergy(long maxExtract, boolean simulate) {
        energy -= Services.ENERGY.ExtractEnergyFromContainer(_rawLoaderDependentContainer, maxExtract, simulate);
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
     * Returns the amount of energy currently stored.
     */
    @Override
    public long getEnergyStored() {
        return energy;
    }

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    @Override
    public long getMaxEnergyStored() {
        return capacity;
    }

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    @Override
    public boolean canExtract() {
        return this.maxExtract > 0;
    }

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    @Override
    public boolean canReceive() {
        return this.maxReceive > 0;
    }

    public boolean isEmpty() {
        return (energy == 0);
    }

    public boolean atMaxCapacity() {
        return (energy == capacity);
    }


    public static final class ExternalContainerBuilder {
        private long _energy;
        private long _capacity;
        private long _maxReceive;
        private long _maxExtract;

        private Object _rawLoaderDependentContainer, _rawBlockEntity;

        public static ExternalContainerBuilder Open() {
            return new ExternalContainerBuilder();
        }

        public ExternalContainerBuilder energy(long energy) {
            this._energy = energy;
            return this;
        }
        public ExternalContainerBuilder capacity(long capacity) {
            this._capacity = capacity;
            return this;
        }
        public ExternalContainerBuilder maxReceive(long maxReceive) {
            this._maxReceive = maxReceive;
            return this;
        }
        public ExternalContainerBuilder maxExtract(long maxExtract) {
            this._maxExtract = maxExtract;
            return this;
        }
        public ExternalContainerBuilder rawLoaderDependentContainer(Object rawLoaderDependentContainer) {
            this._rawLoaderDependentContainer = rawLoaderDependentContainer;
            return this;
        }
        public ExternalContainerBuilder rawBlockEntity(Object rawBlockEntity) {
            this._rawBlockEntity = rawBlockEntity;
            return this;
        }

        public EnergyContainer Build() {
            return new EnergyContainer(_energy, _capacity, _maxReceive, _maxExtract, _rawLoaderDependentContainer, _rawBlockEntity);
        }
    }
}
