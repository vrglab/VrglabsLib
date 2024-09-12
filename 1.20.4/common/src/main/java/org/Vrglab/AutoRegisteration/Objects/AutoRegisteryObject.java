package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.block.entity.BlockEntityType;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AutoRegisteryObject<T> {
    protected boolean resolved;
    protected T registeredObject;
    protected Object rawData;
    protected Supplier<T> supplier;
    protected boolean mcSafe_convertable = true;

    protected String modid;
    protected Map<String, Object> args;

    public AutoRegisteryObject(String modid) {
        this.modid = modid;
        this.args = new HashMap<>();
    }

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
        if(registeredObject == null) {
            if(mcSafe_convertable) {
                registeredObject = (T) TypeTransformer.ObjectToType.accept(rawData);
            } else {
                registeredObject = (T) rawData;
            }
            resolved = true;
        }
        return registeredObject;
    }

    public boolean isMcSafeConvertable() {
        return mcSafe_convertable;
    }

    public void setMcSafeConvertable(boolean mcSafe_convertable) {
        this.mcSafe_convertable = mcSafe_convertable;
    }

    public void setRegistryData(Object data) {
        rawData = data;
    }

    public Object getRawData() {
        return rawData;
    }
}
