package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.village.TradeOffer;

public class RegistryVillagerTrade extends AutoRegisteryObject {

    public RegistryVillagerTrade(String modid, RegistryProfession profession, int level, TradeOffer... offers) {
        super(modid);
        this.args.put("profession", profession);
        this.args.put("level", level);
        this.args.put("offers", offers);
    }
}
