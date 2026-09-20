package org.vrglab.vrglabsLib.Fabric.Platform.Services;

import org.vrglab.vrglabsLib.platform.services.ITypeService;

public class TypeService implements ITypeService {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMcSafeType(Object obj) {
        try {
            return (T) obj;
        } catch (Exception e) {
            return null;
        }
    }
}
