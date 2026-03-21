package org.vrglab.vrglabsLib.Forge.Platform.Services;

import net.minecraftforge.registries.RegistryObject;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.platform.services.ITypeService;

public class TypeService implements ITypeService {

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMcSafeType(Object obj) {
        try {
            if (!(obj instanceof RegistryObject<?>)) {
                Constants.LOG.error("Object {} is not a RegistryObject", obj);
                return null;
            }

            RegistryObject<T> rgObj = ((RegistryObject<T>) obj);
            return rgObj.get();
        } catch (Exception e) {

            Constants.LOG.error("Failed to cast {} to mcSafeType inside Forge", obj.getClass().getTypeName(), e);
            return null;
        }
    }
}
