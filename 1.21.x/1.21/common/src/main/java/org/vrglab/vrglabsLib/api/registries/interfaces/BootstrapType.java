package org.vrglab.vrglabsLib.api.registries.interfaces;

import java.util.UUID;

public enum BootstrapType implements IRegistryType {
    CONFIGUERED_FEAT(UUID.randomUUID()),
    CONFIGUERED_FEAT_ORES(UUID.randomUUID()),
    PLACED_FEAT(UUID.randomUUID()),
    AZURE_ARMOR(UUID.randomUUID()),
    AZURE_ITEM(UUID.randomUUID()),
    AZURE_ID(UUID.randomUUID());

    private final UUID getTypeId;

    BootstrapType(UUID type) {
        getTypeId = type;
    }

    public UUID getTypeId() {
        return getTypeId;
    }
}
