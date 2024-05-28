package org.Vrglab.Modloader.Registration;

import net.minecraft.item.Item;
import org.Vrglab.Modloader.RegistryTypes;
import org.Vrglab.Modloader.Types.ICallBack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Registry {
    private static Map<String, Map<RegistryTypes, ICallBack>> open_registeries = new HashMap<>();
    private static Map<String, Map<RegistryTypes, Object>> ready_to_load_registeries = new HashMap<>();
    
    public static void initRegistry(ICallBack _registery, RegistryTypes _currentRegistryTypes, String modid){
        if(open_registeries.containsKey(modid)){
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        } else{
            open_registeries.put(modid, new HashMap<>()).put(_currentRegistryTypes, _registery);
        }
    }
    
    public static <T extends Item> Object RegisterItem(String name, String Modid, Supplier<T> supplier) {

        if(open_registeries.containsKey(Modid) && open_registeries.get(Modid).containsKey(RegistryTypes.ITEMS))
            return open_registeries.get(Modid).get(RegistryTypes.ITEMS).accept(name, supplier);
        else {
            if(!ready_to_load_registeries.containsKey(Modid))
                ready_to_load_registeries.put(Modid, new HashMap<>()).put(RegistryTypes.ITEMS, )
        }

    }
}
