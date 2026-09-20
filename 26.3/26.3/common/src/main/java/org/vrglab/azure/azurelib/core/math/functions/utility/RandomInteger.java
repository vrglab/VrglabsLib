/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.math.functions.utility;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.Function;

public class RandomInteger extends Function {

    public java.util.Random random;

    public RandomInteger(IValue[] values, String name) throws Exception {
        super(values, name);

        this.random = new java.util.Random();
    }

    @Override
    public int getRequiredArguments() {
        return 2;
    }

    @Override
    public double get() {
        double min = Math.ceil(this.getArg(0));
        double max = Math.floor(this.getArg(1));
        return Math.floor(Math.random() * (max - min) + min);
    }
}
