package mod.TestMod.Items;

import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.level.ItemLike;

import java.util.EnumMap;

public class ArmorMaterials extends org.vrglab.vrglabsLib.api.helpers.ArmorMaterials {

    public static final Holder<ArmorMaterial> AMETHYST = register("amethyst",  Util.make(new EnumMap(ArmorType.class), (pDefense) -> {
        pDefense.put(ArmorType.BOOTS, 2);
        pDefense.put(ArmorType.LEGGINGS, 5);
        pDefense.put(ArmorType.CHESTPLATE, 6);
        pDefense.put(ArmorType.HELMET, 2);
        pDefense.put(ArmorType.BODY, 5);
    }), 9, Holder.direct(SoundEvents.AMETHYST_BLOCK_CHIME), 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[]{
        Items.AMETHYST_SHARD}));
}
