package org.Vrglab.Modloader.Registration;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvent;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.RegistryTypes;

import java.util.*;
import java.util.function.Supplier;

public class Registry {
    private static class UnregisteredData{
        public UnregisteredData( RegistryTypes registry_type, Object... args) {
            this.registry_type = registry_type;
            this.args = args;
        }

        public Object[] args;
        public RegistryTypes registry_type;
        public boolean resolved;

        public Object Obj = null;
    }

    private static Map<String, Map<RegistryTypes, ICallBack>> open_registeries = new HashMap<>();
    private static Map<String, Set<UnregisteredData>> ready_to_load_registeries = new HashMap<>();

    /**
     * Initializes a Modloader's Registry to be used for loading of objects
     * @param _registery The registry code
     * @param _currentRegistryTypes the type of registry
     * @param modid the Mod ID
     *
     * @author Arad Bozorgmehr
     * @since 1.0.0
     */
    public static void initRegistry(ICallBack _registery, RegistryTypes _currentRegistryTypes, String modid){
        if(open_registeries.containsKey(modid)){
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        } else{
            open_registeries.put(modid, new HashMap<>());
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        }
        if(ready_to_load_registeries.containsKey(modid) && ready_to_load_registeries.get(modid).size() > 0) {
            for (UnregisteredData data: ready_to_load_registeries.get(modid)) {
                if(!data.resolved && data.registry_type == _currentRegistryTypes){
                    data.Obj = _registery.accept(data.args);
                }
            }
        }
    }


    public static Object RegisterItem(String name, String Modid, Supplier aNew) {
        return SimpleRegister(RegistryTypes.ITEM, Modid, name, aNew);
    }

    public static Object RegisterBlock(String name, String Modid, Supplier aNew, Item.Settings settings) {
        return SimpleRegister(RegistryTypes.BLOCK, Modid, name, aNew, settings);
    }

    public static Object RegisterItemlessBlock(String name, String Modid, Supplier aNew) {
        return SimpleRegister(RegistryTypes.ITEMLESS_BLOCK, Modid, name, aNew);
    }

    public static Object RegisterPOI(String name, String Modid, Block aNew) {
        return SimpleRegister(RegistryTypes.ITEMLESS_BLOCK, Modid, name, aNew);
    }

    public static Object RegisterProfession(String name, String Modid, String aNew, ImmutableSet<Item> itemImmutableSet, ImmutableSet<Block> blockImmutableSet, SoundEvent sound) {
        return SimpleRegister(RegistryTypes.ITEMLESS_BLOCK, Modid, name, aNew, itemImmutableSet, blockImmutableSet, sound);
    }

    public static Object SimpleRegister(RegistryTypes type, String Modid, Object... args){
        if(open_registeries.containsKey(Modid) && open_registeries.get(Modid).containsKey(type))
            return open_registeries.get(Modid).get(type).accept(args);
        else {
            UnregisteredData data = new UnregisteredData(type, args);
            if(!ready_to_load_registeries.containsKey(Modid)) {
                ready_to_load_registeries.put(Modid, new HashSet<>());
                ready_to_load_registeries.get(Modid).add(data);
            }
            else
                ready_to_load_registeries.get(Modid).add(data);
            return data.Obj;
        }
    }
}
