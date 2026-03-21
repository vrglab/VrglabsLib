package org.vrglab.vrglabsLib.Forge.Utils;

import net.minecraftforge.registries.RegistryObject;

public class Utils extends org.vrglab.vrglabsLib.Utils.Utils {

    public static <T> RegistryObject<T> getRegistryObject(Object obj, Class<T> valueClass) {
        if (!(obj instanceof RegistryObject<?> regObj)) {
            throw new IllegalArgumentException("Object is not a RegistryObject");
        }

        Object value = regObj.get();
        if (!valueClass.isInstance(value)) {
            throw new IllegalArgumentException(
                    "RegistryObject does not contain " + valueClass.getName()
            );
        }

        @SuppressWarnings("unchecked")
        RegistryObject<T> typed = (RegistryObject<T>) regObj;
        return typed;
    }
}
