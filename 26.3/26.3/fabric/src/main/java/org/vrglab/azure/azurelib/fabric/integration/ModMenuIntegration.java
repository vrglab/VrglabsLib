package org.vrglab.azure.azurelib.fabric.integration;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vrglab.azure.azurelib.AzureLibClient;
import org.vrglab.azure.azurelib.common.config.ConfigHolder;
import org.vrglab.azure.azurelib.common.config.ConfigHolderRegistry;
import org.vrglab.azure.azurelib.common.platform.Services;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public Map<String, ConfigScreenFactory<?>> getProvidedConfigScreenFactories() {
        Map<String, ConfigScreenFactory<?>> map = new HashMap<>();
        Map<String, List<ConfigHolder<?>>> byGroup = ConfigHolderRegistry.getConfigGroupingByGroup();
        if (!Services.PLATFORM.isServerEnvironment())
            for (Map.Entry<String, List<ConfigHolder<?>>> entry : byGroup.entrySet()) {
                String group = entry.getKey();
                List<ConfigHolder<?>> configHolders = entry.getValue();
                ConfigScreenFactory<?> factory = parent -> {
                    int i = configHolders.size();
                    if (i > 1) {
                        return AzureLibClient.getConfigScreenByGroup(configHolders, group, parent);
                    } else if (i == 1) {
                        return AzureLibClient.getConfigScreenForHolder(configHolders.get(0), parent);
                    }
                    return null;
                };
                map.put(group, factory);
            }
        return map;
    }
}
