package org.vrglab.azure.azurelib.core.math.functions.easing.cubic;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseOutCubic extends EasingFunction {

    public EaseOutCubic(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return 1 - Math.pow(1 - t, 3);
    }
}
