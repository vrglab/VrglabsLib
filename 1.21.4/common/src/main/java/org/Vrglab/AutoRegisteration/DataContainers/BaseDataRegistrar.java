package org.Vrglab.AutoRegisteration.DataContainers;

import net.minecraft.util.Identifier;
import org.Vrglab.Modloader.enumTypes.IRegistryType;

import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

public abstract class BaseDataRegistrar<T> {
    protected Supplier<T> data;
    protected String Modid;
    protected Identifier id;
    protected UUID registryType;
    protected Map<String, Object> additionalSettings;

    public BaseDataRegistrar(Supplier<T> data, String modid, Identifier id, IRegistryType registryType, Map<String, Object> additionalSettings) {
       this(data, modid, id, registryType.getTypeId(), additionalSettings);
    }

    public BaseDataRegistrar(String modid, Identifier id, UUID registryType, Map<String, Object> additionalSettings) {
        this(null, modid, id, registryType, additionalSettings);
    }

    public BaseDataRegistrar(String modid, Identifier id, IRegistryType registryType, Map<String, Object> additionalSettings) {
        this(null, modid, id, registryType.getTypeId(), additionalSettings);
    }

    public BaseDataRegistrar(Supplier<T> data, String modid, Identifier id, UUID registryType, Map<String, Object> additionalSettings) {
        this.data = data;
        Modid = modid;
        this.id = id;
        this.registryType = registryType;
        this.additionalSettings = additionalSettings;
    }

    public Supplier<T> getData() {
        return data;
    }

    public String getModid() {
        return Modid;
    }

    public Identifier getId() {
        return id;
    }

    public Map<String, Object> getAdditionalSettings() {
        return additionalSettings;
    }

    public UUID getRegistryIntType() {
        return registryType;
    }
}
