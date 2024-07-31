package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.entity.BlockEntityType;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;

import java.util.Map;
import java.util.function.Supplier;

public abstract class AutoRegisteryObject<T> {
    protected boolean resolved;
    protected T registeredObject;
    protected Object rawData;
    protected Supplier<T> supplier;

    protected String modid;
    protected Map<String, Object> args;

    public Supplier<T> getSupplier() {
        return supplier;
    }

    public Map<String, Object> getArgs() {
        return args;
    }

    public String getModid() {
        return modid;
    }

    public boolean isResolved() {
        return resolved;
    }

    public T getRegisteredObject() {
        return (T) TypeTransformer.ObjectToType.accept(rawData);
    }

    public void setRegistryData(Object data) {
        rawData = data;
    }

    public Object getRawData() {
        return rawData;
    }
}
