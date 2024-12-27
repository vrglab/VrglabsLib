package org.Vrglab.neoforge.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.neoforge.Utils.NeoForgeRegistryCreator;

@Mod.EventBusSubscriber(modid = VLModInfo.MOD_ID)
public class VillagerTradeRegisteration {
    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent e){
        NeoForgeRegistryCreator.villagerTradeEventResolver(e, VLModInfo.MOD_ID);
    }
}
