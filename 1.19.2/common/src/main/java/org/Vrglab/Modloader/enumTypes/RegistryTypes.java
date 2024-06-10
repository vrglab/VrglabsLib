package org.Vrglab.Modloader.enumTypes;

public enum RegistryTypes {
    ITEM(0),
    BLOCK(1),
    ITEMLESS_BLOCK(2),
    ENTITY_BLOCK(3),
    MENU(4),
    RECIPE(5),
    ENTITY(6),
    TAG(7),
    BIOM(8),
    STRUCTURE(9),
    POI(10),
    PROFESSION(11),
    TRADE(12),
    CONFIGURED_FEAT_ORE(13),
    PLACED_FEAT(14),
    BIOME_MODIFICATIONS(15),
    BLOCK_ENTITY_TYPE(16),
    SCREEN_HANDLER_TYPE(17),
    HANDLED_SCREEN(18);


    private final int getTypeId;
    RegistryTypes(int type) {
        getTypeId = type;
    }

    public int getTypeId() {
        return getTypeId;
    }

    public static RegistryTypes fromTypeId(int typeId) {
        for (RegistryTypes type : values()) {
            if (type.getTypeId() == typeId) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant with typeId " + typeId);
    }
}
