package org.Vrglab;

import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.VinillaBiomeTypes;
import org.Vrglab.Utils.Modinfo;

import java.util.List;

public class VlGenData {

    public static Object ORE_GEN_FEAT = Registry.RegisterOreConfiguredFeature("block_ore", Modinfo.MOD_ID, OreGenFeatCreationHelper.create().addMatchCase(OreConfiguredFeatures.STONE_ORE_REPLACEABLES, VlBlocks.BLOCK).addMatchCase(OreConfiguredFeatures.DEEPSLATE_ORE_REPLACEABLES, VlBlocks.BLOCK).build(), 5);

    public static Object PLACED_FEAT = Registry.RegisterPlacedFeature("block_ore_placed", Modinfo.MOD_ID, ORE_GEN_FEAT, PlacementModifierCreationHelper.create().HeightRangePlacement(-80, 80).CountModifier(10).build());


    public static void init(){
        Registry.AddBiomeModification("overworld_block_gen", Modinfo.MOD_ID, VinillaBiomeTypes.OVERWORLD, GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEAT);
    }
}
