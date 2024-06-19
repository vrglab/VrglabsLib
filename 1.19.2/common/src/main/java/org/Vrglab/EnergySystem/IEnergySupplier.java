package org.Vrglab.EnergySystem;

public interface IEnergySupplier<T extends EnergyStorage> {
    T getEnergyStorage();
}
