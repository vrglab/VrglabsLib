package org.Vrglab.Modloader.CreationHelpers;

import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.*;
import org.Vrglab.Modloader.Types.ICallBack;
import org.Vrglab.Utils.Modinfo;

import java.util.ArrayList;
import java.util.List;

public class PlacementModifierCreationHelper {
    private List<PlacementModifier> targets;
    public static ICallBack getHeightModifications;

    private PlacementModifierCreationHelper() {
    }

    public static PlacementModifierCreationHelper create() {
        PlacementModifierCreationHelper gen = new PlacementModifierCreationHelper();
        gen.targets = new ArrayList<>(4);
        gen.targets.add(0, null);
        gen.targets.add(1, SquarePlacementModifier.of());
        gen.targets.add(2, null);
        gen.targets.add(3, BiomePlacementModifier.of());
        return gen;
    }

    public PlacementModifierCreationHelper HeightRangePlacement(int lowest, int highest) {
        targets.set(2, (PlacementModifier)getHeightModifications.accept(lowest, highest));
        return this;
    }

    public PlacementModifierCreationHelper CountModifier(int count) {
        if(targets.get(0)!= null && targets.get(0).getClass() == RarityFilterPlacementModifier.class) {
            Modinfo.LOGGER.error("Cant add Count modifier when Rarity modifier is already added");
            return this;
        }

        targets.set(0, CountPlacementModifier.of(count));
        return this;
    }

    public PlacementModifierCreationHelper RarityModifier(int chance) {
        if(targets.get(0)!= null && targets.get(0).getClass() == CountPlacementModifier.class) {
            Modinfo.LOGGER.error("Cant add Rarity modifier when Count modifier is already added");
            return this;
        }

        targets.set(0, RarityFilterPlacementModifier.of(chance));
        return this;
    }

    public List<PlacementModifier> build(){
        return targets;
    }
}
