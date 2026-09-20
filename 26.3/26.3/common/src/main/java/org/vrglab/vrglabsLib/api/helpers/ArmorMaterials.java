package org.vrglab.vrglabsLib.api.helpers;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.equipment.ArmorType;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public abstract class ArmorMaterials {

    protected static Holder<ArmorMaterial> register(String pName, EnumMap<ArmorType, Integer> pDefense, int pEnchantmentValue, Holder<SoundEvent> pEquipSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient) {
        //List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.withDefaultNamespace(pName)));
        return null;
    }

    /*protected static Holder<ArmorMaterial> register(String pName, EnumMap<ArmorItem.Type, Integer> pDefense, int pEnchantmentValue, Holder<SoundEvent> pEquipSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngridient, List<ArmorMaterial.Layer> pLayers) {
        EnumMap<ArmorItem.Type, Integer> enummap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type armoritem$type : ArmorItem.Type.values()) {
            enummap.put(armoritem$type, pDefense.get(armoritem$type));
        }

        return Registry.registerForHolder(BuiltInRegistries.ARMOR_MATERIAL, Identifier.withDefaultNamespace(pName), new ArmorMaterial(enummap, pEnchantmentValue, pEquipSound, pRepairIngridient, pLayers, pToughness, pKnockbackResistance));
    }*/
}
