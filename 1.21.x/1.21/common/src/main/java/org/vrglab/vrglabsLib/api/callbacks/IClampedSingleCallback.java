package org.vrglab.vrglabsLib.api.callbacks;

@FunctionalInterface
public interface IClampedSingleCallback<T, P> {
    T accept(P argument);
}
