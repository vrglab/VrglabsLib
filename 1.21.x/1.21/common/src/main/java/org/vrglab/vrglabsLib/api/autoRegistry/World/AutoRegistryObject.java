package org.vrglab.vrglabsLib.api.autoRegistry.World;

import net.minecraft.resources.ResourceLocation;
import org.vrglab.vrglabsLib.api.helpers.TypeTransformer;

import java.util.Map;
import java.util.function.Supplier;

public abstract class AutoRegistryObject<T> {
    protected boolean resolved;
    protected T registeredObject;
    protected Object rawData;
    protected Supplier<T> supplier;
    protected ResourceLocation id;

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

    public void setId(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return id;
    }

    public void SetModid(String modid) {}

    public Object getRawData() {
        return rawData;
    }
}
