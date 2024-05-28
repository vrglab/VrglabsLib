package org.Vrglab.Modloader.Registration;

import net.minecraft.item.Item;
import org.Vrglab.Modloader.LoaderType;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Registry {
    private static Consumer registery;
    private static LoaderType currentLoaderType;
    
    public static void init(Consumer _registery, LoaderType _currentLoaderType){
        registery = _registery;
        currentLoaderType = _currentLoaderType;
    }
    
    
    public static <T extends Item> Object RegisterItem(String name, Supplier<T> supplier) {

    }
    
    
}
