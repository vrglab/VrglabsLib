/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.math;

/**
 * Group class Simply wraps given {@link IValue} into parenthesis in the {@link #toString()} method.
 */
public class Group implements IValue {

    private final IValue _value;

    public Group(IValue value) {
        this._value = value;
    }

    @Override
    public double get() {
        return this._value.get();
    }

    @Override
    public String toString() {
        return "(" + this._value.toString() + ")";
    }
}
