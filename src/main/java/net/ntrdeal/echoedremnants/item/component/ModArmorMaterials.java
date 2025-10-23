package net.ntrdeal.echoedremnants.item.component;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.item.ModItems;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class ModArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> ROSE_GOLD = register("rose_gold", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.BOOTS, getProtection(ArmorMaterials.GOLD, ArmorItem.Type.BOOTS));
                map.put(ArmorItem.Type.LEGGINGS, getProtection(ArmorMaterials.GOLD, ArmorItem.Type.LEGGINGS));
                map.put(ArmorItem.Type.CHESTPLATE, getProtection(ArmorMaterials.GOLD, ArmorItem.Type.CHESTPLATE));
                map.put(ArmorItem.Type.HELMET, getProtection(ArmorMaterials.GOLD, ArmorItem.Type.HELMET));
                map.put(ArmorItem.Type.BODY, getProtection(ArmorMaterials.GOLD, ArmorItem.Type.BODY));
            }), 16, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> Ingredient.ofItems(ModItems.ROSE_GOLD_INGOT),
            List.of(new ArmorMaterial.Layer(Identifier.of(EchoedRemnants.MOD_ID, "rose_gold"))), 0, 0));

    private static int getProtection(RegistryEntry<ArmorMaterial> material, ArmorItem.Type type) {
        return material.value().getProtection(type);
    }

    public static RegistryEntry<ArmorMaterial> register(String name, Supplier<ArmorMaterial> material) {
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(EchoedRemnants.MOD_ID, name), material.get());
    }

    public static void register(){}
}
