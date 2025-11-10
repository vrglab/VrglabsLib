package org.vrglab.vrglabsLib.api.helpers;

import com.mojang.datafixers.util.Either;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public abstract class ArmorMaterials {

    protected static Holder<ArmorMaterial> register(String pName, EnumMap<ArmorItem.Type, Integer> pDefense, int pEnchantmentValue, Holder<SoundEvent> pEquipSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace(pName)));
        return register(pName, pDefense, pEnchantmentValue, pEquipSound, pToughness, pKnockbackResistance, pRepairIngredient, list);
    }

    protected static Holder<ArmorMaterial> register(String pName, EnumMap<ArmorItem.Type, Integer> pDefense, int pEnchantmentValue, Holder<SoundEvent> pEquipSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngridient, List<ArmorMaterial.Layer> pLayers) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap(ArmorItem.Type.class);

        for(ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, (Integer)pDefense.get(armoritem$type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, ResourceLocation.withDefaultNamespace(pName), new ArmorMaterial(enummap, pEnchantmentValue, pEquipSound, pRepairIngridient, pLayers, pToughness, pKnockbackResistance));
    }
}
