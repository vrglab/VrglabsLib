package org.Vrglab.Modloader.Registration;

import net.minecraft.registry.Registerable;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Modloader.enumTypes.*;

import java.util.*;

public class Bootstrapper {

    private static class UnregisteredData{
        public UnregisteredData(BootstrapType registry_type, Object... args) {
            this.registry_type = registry_type;
            this.args = new ArrayList<>();
            for (Object argdata: args) {
                this.args.add(argdata);
            }
        }

        public List<Object> args;
        public BootstrapType registry_type;
        public boolean resolved;

        public Object Obj = null;
    }

    private static Map<String, Map<BootstrapType, ICallBack>> open_registeries = new HashMap<>();
    private static Map<String, Set<Bootstrapper.UnregisteredData>> ready_to_load_registeries = new HashMap<>();

    public static void initBootstrapper(ICallBack _registery, BootstrapType _currentRegistryTypes, String modid){
        if(open_registeries.containsKey(modid)){
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        } else{
            open_registeries.put(modid, new HashMap<>());
            open_registeries.get(modid).put(_currentRegistryTypes, _registery);
        }
        if(ready_to_load_registeries.containsKey(modid) && ready_to_load_registeries.get(modid).size() > 0) {
            for (Bootstrapper.UnregisteredData data: ready_to_load_registeries.get(modid)) {
                if(!data.resolved && data.registry_type == _currentRegistryTypes){
                    data.Obj = _registery.accept(data.args.toArray());
                    data.resolved = true;
                }
            }
        }
    }

    public static Object SimpleRegister(BootstrapType type, String Modid, Object... args){
        if(open_registeries.containsKey(Modid) && open_registeries.get(Modid).containsKey(type))
            return open_registeries.get(Modid).get(type).accept(args);
        else {
            Bootstrapper.UnregisteredData data = new Bootstrapper.UnregisteredData(type, args);
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
