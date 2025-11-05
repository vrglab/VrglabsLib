package org.Vrglab.VrglabsLib.API.Helpers;

import net.minecraft.world.level.levelgen.structure.Structure;
import org.Vrglab.VrglabsLib.API.Callbacks.ICallBack;

import java.util.ArrayList;
import java.util.List;

public class OreGenFeatCreationHelper {
    //private List<OreFeatureConfig.Target> targets;
    public static ICallBack ObjectBlockToStateConverted;

    private OreGenFeatCreationHelper() {

    }

    public static OreGenFeatCreationHelper create() {
        OreGenFeatCreationHelper gen = new OreGenFeatCreationHelper();
        //gen.targets = new ArrayList<>();
        return gen;
    }

    public OreGenFeatCreationHelper addMatchCase(Structure.GenerationContext feat, Object block) {
        //targets.add(OreFeatureConfig.createTarget(new TagMatchRuleTest(feat.getTagKey()), (BlockState)ObjectBlockToStateConverted.accept(block)));
        return this;
    }

    /*public List<OreFeatureConfig.Target> build(){
        return targets;
    }*/
}
