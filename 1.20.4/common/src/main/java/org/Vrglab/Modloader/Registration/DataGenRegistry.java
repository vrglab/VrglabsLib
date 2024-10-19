package org.Vrglab.Modloader.Registration;

import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.enumTypes.BootstrapType;
import org.Vrglab.Modloader.enumTypes.DataGenType;

import java.util.*;

public class DataGenRegistry {

    private static class UnregisteredData{
        public UnregisteredData(UUID registry_type, Object... args) {
            this.registry_type = registry_type;
            this.args = new ArrayList<>();
            for (Object argdata: args) {
                this.args.add(argdata);
            }
        }

        public List<Object> args;
        public UUID registry_type;
        public boolean resolved;

        public Object Obj = null;
    }

    private static Map<String, Map<UUID, ICallBack>> open_registeries = new HashMap<>();
    private static Map<String, Set<DataGenRegistry.UnregisteredData>> ready_to_load_registeries = new HashMap<>();


    public static void initRegistery(ICallBack _registery, UUID _currentRegistryTypes, String modid){
        if(open_registeries.containsKey(modid)){
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        } else{
            open_registeries.put(modid, new HashMap<>());
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        }
        if(ready_to_load_registeries.containsKey(modid) && ready_to_load_registeries.get(modid).size() > 0) {
            for (DataGenRegistry.UnregisteredData data: ready_to_load_registeries.get(modid)) {
                if(!data.resolved && data.registry_type == _currentRegistryTypes){
                    data.Obj = _registery.accept(data.args.toArray());
                    data.resolved = true;
                }
            }
        }
    }

    public static void initRegistery(ICallBack _registery, DataGenType _currentRegistryTypes, String modid){
        initRegistery(_registery, _currentRegistryTypes.getTypeId(), modid);
    }

    public static Object SimpleRegister(UUID type, String Modid, Object... args){
        if(open_registeries.containsKey(Modid) && open_registeries.get(Modid).containsKey(type))
            return open_registeries.get(Modid).get(type).accept(args);
        else {
            DataGenRegistry.UnregisteredData data = new DataGenRegistry.UnregisteredData(type, args);
            if(!ready_to_load_registeries.containsKey(Modid)) {
                ready_to_load_registeries.put(Modid, new HashSet<>());
                ready_to_load_registeries.get(Modid).add(data);
            }
            else
                ready_to_load_registeries.get(Modid).add(data);
            return data.Obj;
        }
    }

    public static Object SimpleRegister(DataGenType type, String Modid, Object... args){
        return SimpleRegister(type.getTypeId(), Modid, args);
    }

    public static Object RegisterBlock(String Modid, Object block) {
      return SimpleRegister(DataGenType.Block, Modid, block);
    }

    public static Object RegisterItem(String Modid, Object item) {
        return SimpleRegister(DataGenType.Item, Modid, item);
    }

}
