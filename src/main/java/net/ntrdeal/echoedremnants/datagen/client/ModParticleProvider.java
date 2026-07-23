package net.ntrdeal.echoedremnants.datagen.client;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModParticleProvider extends FabricCodecDataProvider<List<Identifier>> {
    public static final Codec<List<Identifier>> PARTICLE_CODEC = Identifier.CODEC.listOf().fieldOf("textures").codec();

    public ModParticleProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, PackOutput.Target.RESOURCE_PACK, "particles", PARTICLE_CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, List<Identifier>> provider, HolderLookup.Provider lookup) {
        provider.accept(EchoedRemnants.id("echoed"), List.of(
                EchoedRemnants.id("echoed_0"),
                EchoedRemnants.id("echoed_1"),
                EchoedRemnants.id("echoed_2"),
                EchoedRemnants.id("echoed_3"),
                EchoedRemnants.id("echoed_4"),
                EchoedRemnants.id("echoed_5")
        ));
    }

    @Override public String getName() {return "particles";}
}
