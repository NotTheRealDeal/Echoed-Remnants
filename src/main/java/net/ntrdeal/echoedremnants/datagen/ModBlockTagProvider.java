package net.ntrdeal.echoedremnants.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.ntrdeal.echoedremnants.block.ModBlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(BlockTags.DAMPENS_VIBRATIONS);

        getOrCreateTagBuilder(ModBlockTags.CANNOT_ECHO)
                .addTag(BlockTags.DAMPENS_VIBRATIONS)
                .add(Blocks.BARRIER,
                        Blocks.BEDROCK,
                        Blocks.END_PORTAL,
                        Blocks.END_PORTAL_FRAME,
                        Blocks.END_GATEWAY,
                        Blocks.COMMAND_BLOCK,
                        Blocks.REPEATING_COMMAND_BLOCK,
                        Blocks.CHAIN_COMMAND_BLOCK,
                        Blocks.STRUCTURE_BLOCK,
                        Blocks.JIGSAW);
    }
}
