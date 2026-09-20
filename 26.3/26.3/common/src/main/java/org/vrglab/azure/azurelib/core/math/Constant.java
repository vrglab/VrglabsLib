/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.math;

/**
 * Constant class This class simply returns supplied in the constructor value
 */
public class Constant implements IValue {

    private double _value;

    public Constant(double value) {
        this._value = value;
    }

    @Override
    public double get() {
        return this._value;
    }

    public void set(double value) {
        this._value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this._value);
    }
}
