package org.Vrglab.Utils;

import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;

public class Utils {

    public static <T> T convertToMcSafeType(Object registry_result){
        return (T)TypeTransformer.ObjectToType.accept(registry_result);
    }
}
