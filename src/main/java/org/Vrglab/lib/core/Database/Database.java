package org.Vrglab.lib.core.Database;

import java.util.*;

public abstract class Database<t> {
    private Map<UUID, Map<String, t>> item_list = new Hashtable<>();

    public void Store(UUID modid, String name, t i) {
        if(!item_list.containsKey(modid)) {
            item_list.put(modid, new HashMap<>());
            if(!item_list.get(modid).containsKey(name)) {
                item_list.get(modid).put(name, i);
            }
        } else {
            if(!item_list.get(modid).containsKey(name)) {
                item_list.get(modid).put(name, i);
            }
        }
    }

    public t GetEntry(UUID modid, String name) {
        return item_list.get(modid).get(name);
    }

    public Collection<t> getDatalist(UUID modid) {
        return item_list.get(modid).values();
    }
}
