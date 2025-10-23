package net.ntrdeal.echoedremnants.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import net.ntrdeal.echoedremnants.block.ModBlocks;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModEnglishProvider extends FabricLanguageProvider {
    public ModEnglishProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(ModItems.ROSE_GOLD_NUGGET, "Rose Gold Nugget");
        translationBuilder.add(ModItems.ROSE_GOLD_INGOT, "Rose Gold Ingot");
        translationBuilder.add(ModBlocks.ROSE_GOLD_BLOCK, "Rose Gold Block");
        translationBuilder.add(ModItems.PENDANT, "Pendant");

        translationBuilder.add(ModEffects.ECHOED.value(), "Echoed");
    }
}
