package org.Vrglab.AutoRegisteration.Objects;

import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;

import java.util.List;

public class RegistryPlacedFeat extends AutoRegisteryObject<List<PlacementModifier>>{

    public RegistryPlacedFeat(String modid, PlacementModifierCreationHelper helper, RegistryConFeat conf_feat){
        super(modid);
        this.args.put("pl_helper", helper);
        this.args.put("conf_feat", conf_feat);
    }
}
