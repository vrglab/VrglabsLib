package org.TestSystem.world;

import net.minecraft.world.gen.GenerationStep;
import org.TestSystem.TestMod;
import org.TestSystem.world.Blocks.TestBlocks;
import org.Vrglab.AutoRegisteration.Annotations.InitializableClass;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBiomeFeat;
import org.Vrglab.AutoRegisteration.Annotations.RegisterBiomeModification;
import org.Vrglab.AutoRegisteration.Annotations.RegisterPlacedFeat;
import org.Vrglab.AutoRegisteration.Objects.RegistryBiomeFeat;
import org.Vrglab.AutoRegisteration.Objects.RegistryBiomeModification;
import org.Vrglab.AutoRegisteration.Objects.RegistryPlacedFeat;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.enumTypes.GenReplaceables;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;


public class TestBiomMods {

    @RegisterBiomeFeat(Name = "biom_mod_con_feat_test")
    public static RegistryBiomeFeat CON_FEAT = new RegistryBiomeFeat(TestMod.MODID,
            OreGenFeatCreationHelper.create().addMatchCase(GenReplaceables.STONE_ORE_REPLACEABLES, TestBlocks.TEST_BLOCK.getRawData()),
            1
            );

    @RegisterPlacedFeat(Name = "biom_mod_placed_feat_test")
    public static RegistryPlacedFeat PLACED_FEAT = new RegistryPlacedFeat(TestMod.MODID,
            PlacementModifierCreationHelper.create().HeightRangePlacement(-80, 10),
            CON_FEAT
    );

    @RegisterBiomeModification(Name = "biom_mod_test")
    public static RegistryBiomeModification BIOME_MODIFICATION = new RegistryBiomeModification(TestMod.MODID, VinillaBiomeTypes.OVERWORLD, GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEAT);
}
