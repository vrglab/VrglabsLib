package org.vrglab.azure.azurelib.core.math.functions.easing.expo;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseOutExpo extends EasingFunction {

    public EaseOutExpo(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return t == 1 ? 1 : 1 - Math.pow(2, -10 * t);
    }
}
