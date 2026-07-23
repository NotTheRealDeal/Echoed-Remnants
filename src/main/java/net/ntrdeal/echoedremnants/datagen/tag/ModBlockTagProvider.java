package net.ntrdeal.echoedremnants.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.references.BlockIds;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.ntrdeal.echoedremnants.block.ModBlockTags;
import net.ntrdeal.echoedremnants.reference.ModBlockItemIds;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(ModBlockTags.CANNOT_ECHO).forceAddTag(BlockTags.DAMPENS_VIBRATIONS).add(
                BlockItemIds.BARRIER.block(), BlockItemIds.BEDROCK.block(),
                BlockItemIds.COMMAND_BLOCK.block(), BlockItemIds.REPEATING_COMMAND_BLOCK.block(),
                BlockItemIds.CHAIN_COMMAND_BLOCK.block(), BlockItemIds.STRUCTURE_BLOCK.block(),
                BlockItemIds.JIGSAW.block(), BlockItemIds.LIGHT.block(),
                BlockIds.END_PORTAL, BlockIds.END_GATEWAY, BlockIds.NETHER_PORTAL
        );

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlockItemIds.ROSE_GOLD_BLOCK.block());
        this.tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlockItemIds.ROSE_GOLD_BLOCK.block());
    }
}
