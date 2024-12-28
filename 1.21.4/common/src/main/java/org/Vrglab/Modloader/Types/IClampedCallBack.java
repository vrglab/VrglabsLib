package org.Vrglab.Modloader.Types;

@FunctionalInterface
public interface IClampedCallBack<T> {
    T accept(Object... args);
}
