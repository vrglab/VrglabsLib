package org.vrglab.azure.azurelib.core.math.functions.easing.quint;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseInOutQuint extends EasingFunction {

    public EaseInOutQuint(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return t < 0.5 ? 16 * t * t * t * t * t : 1 - Math.pow(-2 * t + 2, 5) / 2;
    }
}
