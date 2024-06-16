package org.Vrglab.Modloader.enumTypes.Geckolib;

import org.Vrglab.Modloader.enumTypes.RegistryType;

public enum GeoRegistryTypes implements RegistryType {
    ARMOR(201)
    ;



    private final int getTypeId;
    GeoRegistryTypes(int type) {
        getTypeId = type;
    }

    @Override
    public int getTypeId() {
        return getTypeId;
    }
}
