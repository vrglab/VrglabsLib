package org.Vrglab.TestClasses;

import net.minecraft.world.gen.GenerationStep;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.enumTypes.GenReplaceables;
import org.Vrglab.Modloader.enumTypes.VinillaBiomeTypes;
import org.Vrglab.Utils.VLModInfo;

public class VlGenData {

    public static Object ORE_GEN_FEAT = Registry.RegisterOreConfiguredFeature("block_ore", VLModInfo.MOD_ID, ()->OreGenFeatCreationHelper.create().addMatchCase(GenReplaceables.STONE_ORE_REPLACEABLES, VlBlocks.BLOCK).addMatchCase(GenReplaceables.DEEPSLATE_ORE_REPLACEABLES, VlBlocks.BLOCK).build(), 5);

    public static Object PLACED_FEAT = Registry.RegisterPlacedFeature("block_ore_placed", VLModInfo.MOD_ID, ORE_GEN_FEAT, PlacementModifierCreationHelper.create().HeightRangePlacement(-80, 80).CountModifier(10).build());


    public static void init(){
        Registry.AddBiomeModification("overworld_block_gen", VLModInfo.MOD_ID, VinillaBiomeTypes.OVERWORLD, GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEAT);
    }
}