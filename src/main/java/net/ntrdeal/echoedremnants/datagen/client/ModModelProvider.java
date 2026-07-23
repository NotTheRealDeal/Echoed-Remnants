package net.ntrdeal.echoedremnants.datagen.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.ntrdeal.echoedremnants.block.ModBlocks;
import net.ntrdeal.echoedremnants.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.createTrivialCube(ModBlocks.ROSE_GOLD_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(ModItems.ROSE_GOLD_NUGGET, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.ROSE_GOLD_INGOT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.ROSE_GOLD_PENDANT, ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(ModItems.ROSE_GOLD_MONOCLE, ModelTemplates.FLAT_ITEM);
    }
}
