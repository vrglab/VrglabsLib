/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.Vrglab.azure.azurelib.core.math.functions.classic;

import org.Vrglab.azure.azurelib.core.math.IValue;
import org.Vrglab.azure.azurelib.core.math.functions.Function;

public class Exp extends Function {

    public Exp(IValue[] values, String name) throws Exception {
        super(values, name);
    }

    @Override
    public int getRequiredArguments() {
        return 1;
    }

    @Override
    public double get() {
        return Math.exp(this.getArg(0));
    }
}
