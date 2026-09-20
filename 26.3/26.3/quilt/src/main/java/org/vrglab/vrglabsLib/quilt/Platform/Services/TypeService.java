package org.vrglab.vrglabsLib.quilt.Platform.Services;

public class TypeService implements org.vrglab.vrglabsLib.platform.services.ITypeService {

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
