package org.Vrglab.azure.azurelib.core.math.functions.easing.quad;

import org.Vrglab.azure.azurelib.core.math.IValue;
import org.Vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseInQuad extends EasingFunction {

    public EaseInQuad(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return t * t;
    }
}
