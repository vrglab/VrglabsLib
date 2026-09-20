package org.vrglab.azure.azurelib.common.util.codec;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AzListStreamCodec<T> implements StreamCodec<FriendlyByteBuf, List<T>> {

    private final StreamCodec<FriendlyByteBuf, T> _codec;

    public AzListStreamCodec(StreamCodec<FriendlyByteBuf, T> codec) {
        this._codec = codec;
    }

    @Override
    public @NotNull List<T> decode(FriendlyByteBuf buf) {
        var size = buf.readByte();
        var list = new ArrayList<T>(size);

        for (int i = 0; i < size; i++) {
            list.add(_codec.decode(buf));
        }

        return list;
    }

    @Override
    public void encode(FriendlyByteBuf buf, List<T> elements) {
        buf.writeByte(elements.size());
        elements.forEach(element -> _codec.encode(buf, element));
    }
}
