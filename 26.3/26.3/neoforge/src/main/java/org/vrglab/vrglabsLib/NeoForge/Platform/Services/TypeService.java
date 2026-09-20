package org.vrglab.vrglabsLib.NeoForge.Platform.Services;


import net.neoforged.neoforge.registries.DeferredHolder;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.platform.services.ITypeService;


public class TypeService implements ITypeService {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMcSafeType(Object obj) {
        try {
            if (!(obj instanceof DeferredHolder<?, ?> rgObj)) {
                Constants.LOG.error("Object {} is not a RegistryObject", obj);
                return null;
            }

            return (T) rgObj.get();
        } catch (Exception e) {

            Constants.LOG.error("Failed to cast {} to mcSafeType inside Forge", obj.getClass().getTypeName(), e);
            return null;
        }
    }
}
