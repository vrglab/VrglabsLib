package org.Vrglab.Modloader.enumTypes;

import java.util.UUID;

public enum BootstrapType implements IRegistryType {
    CONFIGUERED_FEAT(UUID.randomUUID()),
    CONFIGUERED_FEAT_ORES(UUID.randomUUID()),
    PLACED_FEAT(UUID.randomUUID());

    private final UUID getTypeId;

    BootstrapType(UUID type) {
        getTypeId = type;
    }

    public UUID getTypeId() {
        return getTypeId;
    }
}
