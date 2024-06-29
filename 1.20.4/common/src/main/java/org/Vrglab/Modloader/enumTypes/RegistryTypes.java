package org.Vrglab.Modloader.enumTypes;

import java.util.UUID;

public enum RegistryTypes implements IRegistryType {
    ITEM(UUID.randomUUID()),
    BLOCK(UUID.randomUUID()),
    ITEMLESS_BLOCK(UUID.randomUUID()),
    ENTITY_BLOCK(UUID.randomUUID()),
    MENU(UUID.randomUUID()),
    RECIPE(UUID.randomUUID()),
    ENTITY(UUID.randomUUID()),
    TAG(UUID.randomUUID()),
    BIOM(UUID.randomUUID()),
    STRUCTURE(UUID.randomUUID()),
    POI(UUID.randomUUID()),
    PROFESSION(UUID.randomUUID()),
    TRADE(UUID.randomUUID()),
    CONFIGURED_FEAT_ORE(UUID.randomUUID()),
    PLACED_FEAT(UUID.randomUUID()),
    BIOME_MODIFICATIONS(UUID.randomUUID()),
    BLOCK_ENTITY_TYPE(UUID.randomUUID()),
    SCREEN_HANDLER_TYPE(UUID.randomUUID()),
    HANDLED_SCREEN(UUID.randomUUID()),
    RECIPE_SERIALIZER(UUID.randomUUID()),
    RECIPE_TYPE(UUID.randomUUID()),
    CREATIVE_MODE_TAB(UUID.randomUUID())
    ;


    private final UUID getTypeId;
    RegistryTypes(UUID type) {
        getTypeId = type;
    }

    public UUID getTypeId() {
        return getTypeId;
    }
}
