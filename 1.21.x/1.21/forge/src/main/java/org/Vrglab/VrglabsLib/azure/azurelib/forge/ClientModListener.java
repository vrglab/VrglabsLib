package org.Vrglab.VrglabsLib.azure.azurelib.forge;

import org.Vrglab.azure.azurelib.AzureLib;
import org.Vrglab.azure.azurelib.common.config.ConfigHolder;
import org.Vrglab.azure.azurelib.common.config.ConfigHolderRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(modid = AzureLib.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModListener {

    @SubscribeEvent
    public static void clientInit(final FMLClientSetupEvent event) {
        Map<String, List<ConfigHolder<?>>> groups = ConfigHolderRegistry.getConfigGroupingByGroup();
        ModList modList = ModList.get();
        for (Map.Entry<String, List<ConfigHolder<?>>> entry : groups.entrySet()) {
            String modId = entry.getKey();
            Optional<? extends ModContainer> optional = modList.getModContainerById(modId);
            optional.ifPresent(modContainer -> {
                List<ConfigHolder<?>> list = entry.getValue();
                /*modContainer.registerExtensionPoint(
                    IConfigScreenFactory.class,
                    (Supplier<Scree>) () -> (container, screen) -> {
                        if (list.size() == 1) {
                            return AzureLibClient.getConfigScreen(list.get(0).getConfigId(), screen);
                        }
                        return AzureLibClient.getConfigScreenByGroup(list, modId, screen);
                    }
                );*/
            });
        }
    }
}
