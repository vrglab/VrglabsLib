package mod.TestMod.Items;

import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ArmorMaterials extends org.vrglab.vrglabsLib.api.helpers.ArmorMaterials {

    public static final Holder<ArmorMaterial> AMETHYST = register("amethyst",  Util.make(new EnumMap(ArmorItem.Type.class), (pDefense) -> {
        pDefense.put(ArmorItem.Type.BOOTS, 2);
        pDefense.put(ArmorItem.Type.LEGGINGS, 5);
        pDefense.put(ArmorItem.Type.CHESTPLATE, 6);
        pDefense.put(ArmorItem.Type.HELMET, 2);
        pDefense.put(ArmorItem.Type.BODY, 5);
    }), 9, Holder.direct(SoundEvents.AMETHYST_BLOCK_CHIME), 0.0F, 0.0F, () -> Ingredient.of(new ItemLike[]{
        Items.AMETHYST_SHARD}));
}
