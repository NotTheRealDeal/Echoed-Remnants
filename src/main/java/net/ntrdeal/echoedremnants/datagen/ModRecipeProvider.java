package net.ntrdeal.echoedremnants.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.block.ModBlocks;
import net.ntrdeal.echoedremnants.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT, 2)
                .input(ConventionalItemTags.GOLD_INGOTS).input(ConventionalItemTags.COPPER_INGOTS)
                .criterion(hasItem(Items.GOLD_INGOT), conditionsFromItem(Items.GOLD_INGOT))
                .offerTo(exporter, Identifier.of(EchoedRemnants.MOD_ID, "alloy_rose_gold_ingot"));

        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter,
                RecipeCategory.MISC, ModItems.ROSE_GOLD_NUGGET,
                RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT,
                "rose_gold_ingot_from_nuggets", "rose_gold_ingot"
        );

        offerReversibleCompactingRecipesWithCompactingRecipeGroup(exporter,
                RecipeCategory.MISC, ModItems.ROSE_GOLD_INGOT,
                RecipeCategory.BUILDING_BLOCKS, ModBlocks.ROSE_GOLD_BLOCK,
                "rose_gold_block_from_rose_gold_ingots", "rose_gold_ingot"
        );

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.PENDANT)
                .pattern("RER")
                .pattern(" R ")
                .input('R', ModItems.ROSE_GOLD_INGOT)
                .input('E', Items.ECHO_SHARD)
                .criterion(hasItem(Items.ECHO_SHARD), conditionsFromItem(Items.ECHO_SHARD))
                .offerTo(exporter);
    }
}
