package org.vrglab.vrglabsLib.api.energy.interfaces;

public interface IEnergySupplier <T extends IEnergyContainer> {
    T getEnergyStorage();
}
