package org.vrglab.vrglabsLib.Utils;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.vrglab.vrglabsLib.api.callbacks.IClampedCallBack;
import org.vrglab.vrglabsLib.api.callbacks.IClampedSingleCallback;
import org.vrglab.vrglabsLib.api.registries.interfaces.IRegistryType;
import org.vrglab.vrglabsLib.core.Constants;
import org.vrglab.vrglabsLib.platform.Services;

import java.util.function.Supplier;

public class Utils {

    /**
     * Takes a generic object and returns an MC Type class (Item, Block ....)
     * @param registryResult The generic Object to convert
     * @return The Found MC Object
     * @param <T> The generic object converted to the MC Type
     */
    public static <T> T convertToMcSafeType(Object registryResult){
        return Services.TYPE_SERVICE.getMcSafeType(registryResult);
    }

    public static <T> T typeCaster(Object obj, Class<T> type){
        try {
            if (type.isInstance(String.class) || type.isAssignableFrom(String.class)) {
                return type.cast(obj.toString());
            }

            return type.cast(obj);
        } catch (Exception e) {
            Constants.LOG.error("Failed to cast {} to {}", obj.getClass().getTypeName(), type.getTypeName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Supplier<T> typeCasterSupplierfied(Object obj, Class<T> type) {
        try {
            return (Supplier<T>) obj;
        } catch (Exception e) {
            Constants.LOG.error("Failed to cast {} to Supplier<{}>", obj.getClass().getTypeName(), type.getTypeName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> IClampedCallBack<T> typeCasterIClampedCallBackafied(Object obj, Class<T> type){
        try {
            return (IClampedCallBack<T>) obj;
        } catch (Exception e) {
            Constants.LOG.error("Failed to cast {} to IClampedCallBack<{}>", obj.getClass().getTypeName(), type.getTypeName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T, P> IClampedSingleCallback<T, P> typeCasterIClampedSingleCallBackafied(Object obj, Class<T> type, Class<P> pType){
        try {
            return (IClampedSingleCallback<T, P>) obj;
        } catch (Exception e) {
            Constants.LOG.error("Failed to cast {} to IClampedSingleCallback<{}, {}>", obj.getClass().getTypeName(), type.getTypeName(), pType.getTypeName(), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T typeCaster(Object obj){
        try {
            return (T) obj;
        } catch (Exception e) {
            Constants.LOG.error("Failed to cast {}", obj.getClass().getTypeName(), e);
            return null;
        }
    }

    public static <T> T nullSafeGetter(Object obj, Supplier<T> inNullCase, Supplier<T> inNormalCase){
        return obj == null ? inNullCase.get() : inNormalCase.get();
    }

    @Deprecated(forRemoval = true, since = "2.0.0-mc1.21")
    public static Item.Properties MakeSafeSettings(Item.Properties settings, IRegistryType registry, ResourceLocation id){
        return settings;
    }

    @Deprecated(forRemoval = true, since = "2.0.0-mc1.21")
    public static BlockBehaviour.Properties MakeSafeSettings(BlockBehaviour.Properties settings, IRegistryType registry, ResourceLocation id){
        return settings;
    }
}
