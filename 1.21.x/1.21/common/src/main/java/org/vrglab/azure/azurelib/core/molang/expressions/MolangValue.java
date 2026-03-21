/**
 * This class is a fork of the matching class found in the Geckolib repository. Original source:
 * https://github.com/bernie-g/geckolib Copyright © 2024 Bernie-G. Licensed under the MIT License.
 * https://github.com/bernie-g/geckolib/blob/main/LICENSE
 */
package org.vrglab.azure.azurelib.core.molang.expressions;

import org.vrglab.azure.azurelib.core.math.Constant;
import org.vrglab.azure.azurelib.core.math.IValue;
import org.vrglab.azure.azurelib.core.molang.MolangParser;

/**
 * Molang extension for the {@link IValue} system. Used to handle values and expressions specific to Molang
 * deserialization
 */
public class MolangValue implements IValue {

    private final IValue _value;

    private final boolean _returns;

    public MolangValue(IValue value) {
        this(value, false);
    }

    public MolangValue(IValue value, boolean isReturn) {
        this._value = value;
        this._returns = isReturn;
    }

    @Override
    public double get() {
        return this._value.get();
    }

    public IValue getValueHolder() {
        return this._value;
    }

    public boolean isReturnValue() {
        return this._returns;
    }

    public boolean isConstant() {
        return getClass() == MolangValue.class && _value instanceof Constant;
    }

    @Override
    public String toString() {
        return (this._returns ? MolangParser.RETURN : "") + this._value.toString();
    }
}
