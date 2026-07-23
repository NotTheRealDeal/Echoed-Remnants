package net.ntrdeal.echoedremnants.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.ntrdeal.echoedremnants.item.ModItemTags;
import net.ntrdeal.echoedremnants.reference.ModItemIds;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(ItemTags.HEAD_ARMOR).add(ModItemIds.ROSE_GOLD_MONOCLE);
        this.tag(ItemTags.CHEST_ARMOR).add(ModItemIds.ROSE_GOLD_PENDANT);
        this.tag(ModItemTags.REPAIRS_ROSE_GOLD_ARMOR).add(ModItemIds.ROSE_GOLD_INGOT);

        this.tag(ItemTags.TRIMMABLE_ARMOR).remove(ModItemIds.ROSE_GOLD_MONOCLE, ModItemIds.ROSE_GOLD_PENDANT);
    }
}
