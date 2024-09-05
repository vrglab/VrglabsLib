package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.sound.SoundEvent;
import net.minecraft.village.VillagerProfession;

public class RegistryProfession extends AutoRegisteryObject<VillagerProfession> {

    public RegistryProfession(String modid, RegistryPOI poi, RegistryItem[] immutableItems, RegistryBlock[] immutableBlocks, SoundEvent event) {
        super(modid);
        this.args.put("immutableItems", immutableItems);
        this.args.put("immutableBlocks", immutableBlocks);
        this.args.put("event", event);
        this.args.put("poi", poi);
    }
}
