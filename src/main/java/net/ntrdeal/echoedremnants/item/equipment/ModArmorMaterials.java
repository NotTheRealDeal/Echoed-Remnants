package net.ntrdeal.echoedremnants.item.equipment;

import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.ntrdeal.echoedremnants.item.ModItemTags;

public class ModArmorMaterials {
    public static final ArmorMaterial ROSE_GOLD = new ArmorMaterial(
            14, ArmorMaterials.GOLD.defense(),
            ArmorMaterials.GOLD.enchantmentValue(), ArmorMaterials.GOLD.equipSound(),
            ArmorMaterials.GOLD.toughness(), ArmorMaterials.GOLD.knockbackResistance(),
            ModItemTags.REPAIRS_ROSE_GOLD_ARMOR, ModEquipmentAssets.ROSE_GOLD
    );
}
