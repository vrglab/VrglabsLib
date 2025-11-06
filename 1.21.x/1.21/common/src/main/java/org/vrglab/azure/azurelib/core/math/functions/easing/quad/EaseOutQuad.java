package org.vrglab.azure.azurelib.core.math.functions.easing.quad;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.easing.EasingFunction;

public class EaseOutQuad extends EasingFunction {

    public EaseOutQuad(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    protected double ease(double t) {
        return 1 - (1 - t) * (1 - t);
    }
}
