package org.Vrglab.forge.events;

import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.Vrglab.Utils.VLModInfo;
import org.Vrglab.forge.Utils.ForgeRegistryCreator;

@Mod.EventBusSubscriber(modid = VLModInfo.MOD_ID)
public class VillagerTradeRegisteration {

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent e){
        ForgeRegistryCreator.villagerTradeEventResolver(e, VLModInfo.MOD_ID);
    }
}
