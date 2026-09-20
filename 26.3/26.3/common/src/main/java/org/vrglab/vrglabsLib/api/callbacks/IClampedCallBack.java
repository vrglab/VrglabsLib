package org.vrglab.vrglabsLib.api.callbacks;

@FunctionalInterface
public interface IClampedCallBack<T> {
    T accept(Object... args);
}
