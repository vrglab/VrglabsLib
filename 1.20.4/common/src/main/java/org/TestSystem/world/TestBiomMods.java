package org.TestSystem.world;

import net.minecraft.world.gen.GenerationStep;
import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.AutoRegisteration.Annotations.InitializableClass;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.enumTypes.GenReplaceables;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;

@InitializableClass
public class TestBiomMods {

    public static Object CON_FEAT = Registry.RegisterOreConfiguredFeature("biom_mod_con_feat_test", TestMod.MODID, () ->
            OreGenFeatCreationHelper.create().addMatchCase(GenReplaceables.STONE_ORE_REPLACEABLES, TestBlocks.TEST_BLOCK.getRawData()).build(), 1);

    public static Object PLACED_FEAT = Registry.RegisterPlacedFeature("biom_mod_placed_feat_test", TestMod.MODID, CON_FEAT,
            PlacementModifierCreationHelper.create().HeightRangePlacement(-80, 10).build());

    public static void init() {
        Registry.AddBiomeModification("biom_mod_test", TestMod.MODID, VinillaBiomeTypes.OVERWORLD, GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEAT);
    }
}
