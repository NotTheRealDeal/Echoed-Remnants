package net.ntrdeal.echoedremnants.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroups;
import net.ntrdeal.echoedremnants.block.ModBlocks;

public class ModItemGroups {
    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(ModItems.ROSE_GOLD_NUGGET);
            entries.add(ModItems.ROSE_GOLD_INGOT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(ModItems.MONOCLE);
            entries.add(ModItems.PENDANT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.ROSE_GOLD_BLOCK);
        });
    }
}
