package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;

import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

public class RegistryBiomeFeat extends AutoRegisteryObject<List<OreFeatureConfig.Target>> {

    public RegistryBiomeFeat(String modid, int size, Supplier<OreGenFeatCreationHelper> creationHelper) {
        this.modid = modid;
        this.supplier = ()-> creationHelper.get().build();
        this.args = new HashMap<>();
        this.args.put("size", size);
    }
}
