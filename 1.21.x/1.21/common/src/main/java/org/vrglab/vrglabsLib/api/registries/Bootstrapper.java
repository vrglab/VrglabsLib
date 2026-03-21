package org.vrglab.vrglabsLib.api.registries;

import org.vrglab.vrglabsLib.api.callbacks.ICallBack;

import java.util.*;

public class Bootstrapper {

    private static class UnregisteredData {
         UnregisteredData(UUID registryType, Object... args) {
            this.registryType = registryType;
            this.args = new ArrayList<>();
            for (Object argdata: args) {
                this.args.add(argdata);
            }
        }

        public List<Object> args;
        public UUID registryType;
        public boolean resolved;

        public Object obj = null;
    }

    private static final Map<String, Map<UUID, ICallBack>> OPEN_REGISTRIES = new HashMap<>();
    private static final Map<String, Set<Bootstrapper.UnregisteredData>> READY_TO_LOAD_REGISTRIES = new HashMap<>();

    public static void initBootstrapper(ICallBack registry, UUID currentRegistryTypes, String modid){
        if (OPEN_REGISTRIES.containsKey(modid)){
            OPEN_REGISTRIES.get(modid).put(currentRegistryTypes, registry);
        } else {
            OPEN_REGISTRIES.put(modid, new HashMap<>());
            OPEN_REGISTRIES.get(modid).put(currentRegistryTypes, registry);
        }
        if (READY_TO_LOAD_REGISTRIES.containsKey(modid) && !READY_TO_LOAD_REGISTRIES.get(modid).isEmpty()) {
            for (Bootstrapper.UnregisteredData data: READY_TO_LOAD_REGISTRIES.get(modid)) {
                if (!data.resolved && data.registryType == currentRegistryTypes){
                    data.obj = registry.accept(data.args.toArray());
                    data.resolved = true;
                }
            }
        }
    }

   /* public static void initBootstrapper(ICallBack _registery, Bootstrap _currentRegistryTypes, String modid){
        initBootstrapper(_registery, _currentRegistryTypes.getTypeId(), modid);
    }*/

    public static Object SimpleRegister(UUID type, String modId, Object... args) {
        if (OPEN_REGISTRIES.containsKey(modId) && OPEN_REGISTRIES.get(modId).containsKey(type)) {
            return OPEN_REGISTRIES.get(modId).get(type).accept(args);
        }else {
            Bootstrapper.UnregisteredData data = new Bootstrapper.UnregisteredData(type, args);
            if (!READY_TO_LOAD_REGISTRIES.containsKey(modId)) {
                READY_TO_LOAD_REGISTRIES.put(modId, new HashSet<>());
                READY_TO_LOAD_REGISTRIES.get(modId).add(data);
            } else {
                READY_TO_LOAD_REGISTRIES.get(modId).add(data);
            }
            return data.obj;
        }
    }

   /* public static Object SimpleRegister(BootstrapType type, String Modid, Object... args){
        return SimpleRegister(type.getTypeId(), Modid, args);
    }*/
}
