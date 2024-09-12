package org.Vrglab.Utils;

import org.Vrglab.AutoRegisteration.Objects.AutoRegisteryObject;
import org.Vrglab.Modloader.CreationHelpers.TypeTransformer;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static <T> T convertToMcSafeType(Object registry_result){
        return (T)TypeTransformer.ObjectToType.accept(registry_result);
    }

    public static <T, L extends AutoRegisteryObject> T[] convertAGOToRD(L[] AGO, T[] empty_array) {
        List<T> RD = new ArrayList<T>();
        for (L object : AGO) {
            RD.add(convertAGOToRD(object));
        }
        return RD.toArray(empty_array);
    }

    public static <T, L extends AutoRegisteryObject> T convertAGOToRD(L AGO) {
        if(AGO.isMcSafeConvertable()) {
           return convertToMcSafeType(AGO.getRawData());
        } else {
           return (T)AGO.getRawData();
        }
    }
}
