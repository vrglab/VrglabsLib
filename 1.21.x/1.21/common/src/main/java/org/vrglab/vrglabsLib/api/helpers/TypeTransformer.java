package org.vrglab.vrglabsLib.api.helpers;

import org.apache.commons.lang3.NotImplementedException;
import org.vrglab.vrglabsLib.api.callbacks.ICallBack;
import org.jetbrains.annotations.ApiStatus;

/**
 * @deprecated use {@link org.vrglab.vrglabsLib.Utils.Utils#convertToMcSafeType(Object)} instead
 */
@ApiStatus.Internal
@Deprecated(forRemoval = true, since = "2.0.0-mc1.21")
public class TypeTransformer {
    public static ICallBack ObjectToType = new ICallBack() {
        @Override
        public Object accept(Object... args) {
            throw new NotImplementedException("ObjectToType Is obsolete and will be removed in a future version. please use org.vrglab.vrglabsLib.Utils.Utils.convertToMcSafeType instead");
        }
    };

}
