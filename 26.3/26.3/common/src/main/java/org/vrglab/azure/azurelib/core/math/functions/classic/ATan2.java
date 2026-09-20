/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.math.functions.classic;

import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.math.functions.Function;

/**
 * Absolute value function
 */
public class ATan2 extends Function {

    public ATan2(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 2;
    }

    @Override
    public double get() {
        return Math.atan2(this.getArg(0), this.getArg(1));
    }
}
