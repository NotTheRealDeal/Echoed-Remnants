package net.ntrdeal.echoedremnants.datagen.client.language;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.ntrdeal.echoedremnants.block.ModBlocks;
import net.ntrdeal.echoedremnants.component.echoed.EchoedEffect;
import net.ntrdeal.echoedremnants.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModEnglishProvider extends FabricLanguageProvider {
    public ModEnglishProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, "en_us", lookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider lookup, TranslationBuilder builder) {
        builder.add(ModItems.ROSE_GOLD_NUGGET, "Rose Gold Nugget");
        builder.add(ModItems.ROSE_GOLD_INGOT, "Rose Gold Ingot");
        builder.add(ModBlocks.ROSE_GOLD_BLOCK, "Rose Gold Block");
        builder.add(ModItems.ROSE_GOLD_PENDANT, "Rose Gold Pendant");
        builder.add(ModItems.ROSE_GOLD_MONOCLE, "Rose Gold Monocle");


        builder.add("death.attack.echoed", "%1$s faded away.");
        builder.add("death.attack.echoed.player", "%1$s faded away whilst fighting %2$s.");

        builder.add(EchoedEffect.HOLDER.value(), "Echoed");
    }
}
