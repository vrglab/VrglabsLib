package org.Vrglab.EnergySystem;

import net.minecraft.nbt.NbtType;

public interface IEnergyContainer {
    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of energy to be inserted.
     * @param simulate
     *            If TRUE, the insertion will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) accepted by the storage.
     */
    long receiveEnergy(long maxReceive, boolean simulate);

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract
     *            Maximum amount of energy to be extracted.
     * @param simulate
     *            If TRUE, the extraction will only be simulated.
     * @return Amount of energy that was (or would have been, if simulated) extracted from the storage.
     */
    long extractEnergy(long maxExtract, boolean simulate);

    /**
     * Adds energy to the storage. Returns quantity of energy that was accepted.
     *
     * @param maxReceive
     *            Maximum amount of energy to be inserted.
     * @return Amount of energy that was accepted by the storage.
     */
    long receiveEnergy(long maxReceive);

    /**
     * Removes energy from the storage. Returns quantity of energy that was removed.
     *
     * @param maxExtract
     *            Maximum amount of energy to be extracted.
     * @return Amount of energy that was (or would have been) extracted from the storage.
     */
    long extractEnergy(long maxExtract);

    /**
     * Returns the amount of energy currently stored.
     */
    long getEnergyStored();

    /**
     * Returns the maximum amount of energy that can be stored.
     */
    long getMaxEnergyStored();

    /**
     * Returns if this storage can have energy extracted.
     * If this is false, then any calls to extractEnergy will return 0.
     */
    boolean canExtract();

    /**
     * Used to determine if this storage can receive energy.
     * If this is false, then any calls to receiveEnergy will return 0.
     */
    boolean canReceive();
}
