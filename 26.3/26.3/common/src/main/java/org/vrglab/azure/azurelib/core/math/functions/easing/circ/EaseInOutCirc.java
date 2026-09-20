package org.vrglab.azure.azurelib.core.math.functions.easing.circ;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseInOutCirc extends EasingFunction {

    public EaseInOutCirc(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return t < 0.5
            ? (1 - Math.sqrt(1 - Math.pow(2 * t, 2))) / 2
            : (Math.sqrt(1 - Math.pow(-2 * t + 2, 2)) + 1) / 2;
    }
}
