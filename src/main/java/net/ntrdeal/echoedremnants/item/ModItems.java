package net.ntrdeal.echoedremnants.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.item.component.ModArmorMaterials;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;

public class ModItems {
    public static final Item ROSE_GOLD_NUGGET = register("rose_gold_nugget", new Item(new Item.Settings()));
    public static final Item ROSE_GOLD_INGOT = register("rose_gold_ingot", new Item(new Item.Settings()));


    public static final ArmorItem PENDANT = register("pendant", new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.CHESTPLATE,
            new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(14))));

    public static final ArmorItem MONOCLE = register("monocle", new ArmorItem(ModArmorMaterials.ROSE_GOLD, ArmorItem.Type.HELMET,
            new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(14))));

    public static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(EchoedRemnants.MOD_ID, name), item);
    }

    public static void register(){
        ModDataComponents.register();
        ModArmorMaterials.register();
        ModItemGroups.register();
    }
}
