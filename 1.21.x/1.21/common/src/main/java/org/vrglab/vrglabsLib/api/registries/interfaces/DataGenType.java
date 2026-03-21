package org.vrglab.vrglabsLib.api.registries.interfaces;

import java.util.UUID;

public enum DataGenType implements IRegistryType {
    Block(UUID.randomUUID()),
    Item(UUID.randomUUID())
    ;

    private final UUID _getTypeId;
    DataGenType(UUID type) {
        _getTypeId = type;
    }

    public UUID getTypeId() {
        return _getTypeId;
    }
}
