package org.Vrglab.VrglabsLib.API.Callbacks;

@FunctionalInterface
public interface IClampedCallBack<T> {
    T accept(Object... args);
}
