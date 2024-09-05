package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;

import java.util.List;
import java.util.function.Supplier;

public class RegistryConFeat extends AutoRegisteryObject {

    public RegistryConFeat(String modid, int size, Supplier<OreGenFeatCreationHelper> creationHelper) {
        super(modid);
        this.supplier = ()-> creationHelper.get().build();
        this.args.put("size", size);
    }
}
