/**
 * This class is a fork of the matching class found in the Configuration repository. Original source:
 * https://github.com/Toma1O6/Configuration Copyright © 2024 Toma1O6. Licensed under the MIT License.
 */
package org.vrglab.azure.azurelib.common.config.value;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import org.vrglab.azure.azurelib.common.config.adapter.TypeAdapter;

public final class ValueData<T> implements IDescriptionProvider {

    private final String _id;

    private final String[] _tooltip;

    private final T _defaultValue;

    private final TypeAdapter.AdapterContext _context;

    private final Class<T> _valueType;

    @Nullable
    private ConfigValue<?> _parent;

    private ValueData(String id, String[] tooltip, T defaultValue, TypeAdapter.AdapterContext context) {
        this._id = id;
        this._tooltip = tooltip;
        this._defaultValue = defaultValue;
        this._context = context;
        this._valueType = (Class<T>) defaultValue.getClass();
    }

    public static <V> ValueData<V> of(String id, V value, TypeAdapter.AdapterContext setter, String... comments) {
        return new ValueData<>(id, comments, Objects.requireNonNull(value), setter);
    }

    public String getId() {
        return _id;
    }

    @Override
    public String[] getDescription() {
        return _tooltip;
    }

    public T getDefaultValue() {
        return _defaultValue;
    }

    public void setValueToMemory(Object value) {
        this._context.setFieldValue(value);
    }

    @Nullable
    public ConfigValue<?> getParent() {
        return _parent;
    }

    public void setParent(@Nullable ConfigValue<?> parent) {
        this._parent = parent;
    }

    public TypeAdapter.AdapterContext getContext() {
        return _context;
    }

    public Class<T> getValueType() {
        return _valueType;
    }
}
