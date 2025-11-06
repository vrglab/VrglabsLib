package org.Vrglab.azure.azurelib.core.math.functions.easing.quart;

import org.Vrglab.azure.azurelib.core.math.IValue;
import org.Vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseInQuart extends EasingFunction {

    public EaseInQuart(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return t * t * t * t;
    }
}
