package org.Vrglab.Modloader.CreationHelpers;

import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.Vrglab.Modloader.Types.ICallBack;

import java.util.ArrayList;
import java.util.List;

public class OreGenFeatCreationHelper {
    private List<OreFeatureConfig.Target> targets;
    public static ICallBack ObjectBlockToStateConverted;

    private OreGenFeatCreationHelper() {

    }

    public static OreGenFeatCreationHelper create() {
        OreGenFeatCreationHelper gen = new OreGenFeatCreationHelper();
        gen.targets = new ArrayList<>();
        return gen;
    }

    public OreGenFeatCreationHelper addMatchCase(RuleTest feat, Object block) {
        targets.add(OreFeatureConfig.createTarget(feat, (BlockState)ObjectBlockToStateConverted.accept(block)));
        return this;
    }

    public List<OreFeatureConfig.Target> build(){
        return targets;
    }
}
