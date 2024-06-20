package org.Vrglab.Utils;

import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;

public class Utils {

    public static Object convertToMcSafeType(Object registry_result){
        return TypeTransformer.ObjectToType.accept(registry_result);
    }
}
