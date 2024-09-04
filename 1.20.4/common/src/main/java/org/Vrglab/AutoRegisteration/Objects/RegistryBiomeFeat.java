package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;

import java.util.HashMap;
import java.util.List;

public class RegistryBiomeFeat extends AutoRegisteryObject<List<OreFeatureConfig.Target>> {

    public RegistryBiomeFeat(String modid, OreGenFeatCreationHelper helper, int size) {
        this.modid = modid;
        this.supplier = () -> helper.build();
        this.args = new HashMap<>();
        this.args.put("size", size);
    }
}
