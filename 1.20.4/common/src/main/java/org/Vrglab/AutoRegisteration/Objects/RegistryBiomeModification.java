package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.gen.GenerationStep;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;

import java.util.HashMap;

public class RegistryBiomeModification extends AutoRegisteryObject {

    public RegistryBiomeModification(String modid, VinillaBiomeTypes biomeType, GenerationStep.Feature gen_step, RegistryPlacedFeat placedFeat) {
        super(modid);
        this.args.put("biome_type", biomeType);
        this.args.put("gen_step", gen_step);
        this.args.put("placed_feat", placedFeat);
    }
}
