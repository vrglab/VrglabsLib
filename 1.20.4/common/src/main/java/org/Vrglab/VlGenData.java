package org.Vrglab;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import org.Vrglab.Modloader.CreationHelpers.OreGenFeatCreationHelper;
import org.Vrglab.Modloader.CreationHelpers.PlacementModifierCreationHelper;
import org.Vrglab.Modloader.Registration.Registry;
import org.Vrglab.Modloader.enumTypes.*;
import org.Vrglab.Utils.Modinfo;

public class VlGenData {

    public static Object ORE_GEN_FEAT = Registry.RegisterOreConfiguredFeature("block_ore", Modinfo.MOD_ID, ()->OreGenFeatCreationHelper.create().addMatchCase(BlockTags.STONE_ORE_REPLACEABLES, VlBlocks.BLOCK).addMatchCase(BlockTags.DEEPSLATE_ORE_REPLACEABLES, VlBlocks.BLOCK).build(), 5);

    public static Object PLACED_FEAT = Registry.RegisterPlacedFeature("block_ore_placed", Modinfo.MOD_ID, ORE_GEN_FEAT, PlacementModifierCreationHelper.create().HeightRangePlacement(-80, 80).CountModifier(10).build());


    public static void init(){
        Registry.AddBiomeModification("overworld_block_gen", Modinfo.MOD_ID, VinillaBiomeTypes.OVERWORLD, GenerationStep.Feature.UNDERGROUND_ORES, PLACED_FEAT);
    }
}
