package org.Vrglab.Modloader.enumTypes;

import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.structure.rule.RuleTest;

public enum GenReplaceables {
     BASE_STONE_OVERWORLD(OreConfiguredFeatures.BASE_STONE_OVERWORLD),
     STONE_ORE_REPLACEABLES(OreConfiguredFeatures.STONE_ORE_REPLACEABLES),
     DEEPSLATE_ORE_REPLACEABLES(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES),
     NETHERRACK(OreConfiguredFeatures.NETHERRACK),
     BASE_STONE_NETHER(OreConfiguredFeatures.BASE_STONE_NETHER);
     private final RuleTest ruleTest;

     GenReplaceables(RuleTest ruleTest) {
          this.ruleTest = ruleTest;
     }

     public RuleTest getRuleTest() {
          return ruleTest;
     }
}
