package org.vrglab.vrglabsLib.NeoForge.Utils;

import net.neoforged.neoforge.registries.DeferredHolder;

public class Utils extends org.vrglab.vrglabsLib.Utils.Utils {

    public static <T> DeferredHolder<T, ?> getDeferredHolderObject(Object obj, Class<T> valueClass) {
        if (!(obj instanceof DeferredHolder<?, ?> regObj)) {
            throw new IllegalArgumentException("Object is not a RegistryObject");
        }

        Object value = regObj.get();
        if (!valueClass.isInstance(value)) {
            throw new IllegalArgumentException(
                    "RegistryObject does not contain " + valueClass.getName()
            );
        }

        @SuppressWarnings("unchecked")
        DeferredHolder<T, ?> typed = (DeferredHolder<T, ?>) regObj;
        return typed;
    }
}
