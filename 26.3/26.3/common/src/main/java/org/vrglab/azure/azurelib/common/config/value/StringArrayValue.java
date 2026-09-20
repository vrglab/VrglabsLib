/**
 * This class is a fork of the matching class found in the Configuration repository. Original source:
 * https://github.com/Toma1O6/Configuration Copyright © 2024 Toma1O6. Licensed under the MIT License.
 */
package org.vrglab.azure.azurelib.common.config.value;

import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.vrglab.azure.azurelib.AzureLib;
import org.vrglab.azure.azurelib.common.config.ConfigUtils;
import org.vrglab.azure.azurelib.common.config.Configurable;
import org.vrglab.azure.azurelib.common.config.adapter.TypeAdapter;
import org.vrglab.azure.azurelib.common.config.exception.ConfigValueMissingException;
import org.vrglab.azure.azurelib.common.config.format.IConfigFormat;
import org.vrglab.azure.azurelib.common.config.io.ConfigIO;

public class StringArrayValue extends ConfigValue<String[]> implements ArrayValue {

    private boolean _fixedSize;

    private Pattern _pattern;

    private String _defaultElementValue = "";

    public StringArrayValue(ValueData<String[]> valueData) {
        super(valueData);
    }

    @Override
    public boolean isFixedSize() {
        return _fixedSize;
    }

    @Override
    protected void readFieldData(Field field) {
        this._fixedSize = field.getAnnotation(Configurable.FixedSize.class) != null;
        Configurable.StringPattern stringPattern = field.getAnnotation(Configurable.StringPattern.class);
        if (stringPattern != null) {
            String value = stringPattern.value();
            this._defaultElementValue = stringPattern.defaultValue();
            try {
                this._pattern = Pattern.compile(value, stringPattern.flags());
            } catch (IllegalArgumentException e) {
                AzureLib.LOGGER.error(
                    ConfigIO.MARKER,
                    "Invalid @StringPattern value for {} field - {}",
                    this.getId(),
                    e
                );
            }
            if (this._pattern != null && !this._pattern.matcher(this._defaultElementValue).matches()) {
                throw new IllegalArgumentException(
                    String.format(
                        "Invalid config default value '%s' for field '%s' - does not match required pattern \\%s\\",
                        this._defaultElementValue,
                        this.getId(),
                        this._pattern.toString()
                    )
                );
            }
        }
    }

    @Override
    protected String[] getCorrectedValue(String[] in) {
        String[] defaultArray = this.valueData.getDefaultValue();
        if (this._fixedSize && (in.length != defaultArray.length)) {
            ConfigUtils.logArraySizeCorrectedMessage(this.getId(), Arrays.toString(in), Arrays.toString(defaultArray));
            return defaultArray;
        }
        if (this._pattern != null) {
            for (int i = 0; i < in.length; i++) {
                String string = in[i];
                if (!this._pattern.matcher(string).matches()) {
                    ConfigUtils.logCorrectedMessage(this.getId() + "[" + i + "]", string, this._defaultElementValue);
                    in[i] = this._defaultElementValue;
                }
            }
        }
        return in;
    }

    public String getDefaultElementValue() {
        return _defaultElementValue;
    }

    @Override
    protected void serialize(IConfigFormat format) {
        format.writeStringArray(this.getId(), this.get());
    }

    @Override
    protected void deserialize(IConfigFormat format) throws ConfigValueMissingException {
        this.set(format.readStringArray(this.getId()));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        String[] strings = this.get();
        for (int i = 0; i < strings.length; i++) {
            builder.append(this.elementToString(strings[i]));
            if (i < strings.length - 1) {
                builder.append(",");
            }
        }
        builder.append("]");
        return builder.toString();
    }

    public static final class Adapter extends TypeAdapter {

        @Override
        public void encodeToBuffer(ConfigValue<?> value, FriendlyByteBuf buffer) {
            String[] arr = (String[]) value.get();
            buffer.writeInt(arr.length);
            for (String v : arr) {
                buffer.writeUtf(v);
            }
        }

        @Override
        public Object decodeFromBuffer(ConfigValue<?> value, FriendlyByteBuf buffer) {
            String[] arr = new String[buffer.readInt()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = buffer.readUtf();
            }
            return arr;
        }

        @Override
        public ConfigValue<?> serialize(
            String name,
            String[] comments,
            Object value,
            TypeSerializer serializer,
            AdapterContext context
        ) throws IllegalAccessException {
            return new StringArrayValue(ValueData.of(name, (String[]) value, context, comments));
        }
    }
}
