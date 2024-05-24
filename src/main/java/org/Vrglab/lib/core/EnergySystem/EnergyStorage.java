package org.Vrglab.lib.core.EnergySystem;

public abstract class EnergyStorage extends net.minecraftforge.energy.EnergyStorage {
    public EnergyStorage(int capacity) {
        super(capacity);
    }

    public EnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int recieved_energy = super.receiveEnergy(maxReceive, simulate);
        if(recieved_energy != 0) {
            onEnergyChanged(recieved_energy);
        }
        return recieved_energy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extracted_energy = super.extractEnergy(maxExtract, simulate);
        if(extracted_energy != 0) {
            onEnergyChanged(extracted_energy);
        }
        return extracted_energy;
    }

    public int setEnergy(int amnt) {
        this.energy = amnt;
        return amnt;
    }

    public abstract void onEnergyChanged(int amnt);
}
