package net.ntrdeal.echoedremnants.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.ntrdeal.echoedremnants.reference.ModMobEffectIds;
import net.ntrdeal.realapi.tag.RealMobEffectTags;

import java.util.concurrent.CompletableFuture;

public class ModMobEffectTagProvider extends FabricTagsProvider<MobEffect> {
    public ModMobEffectTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Registries.MOB_EFFECT, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        this.tag(RealMobEffectTags.PLAYER_ONLY).add(ModMobEffectIds.ECHOED);
        this.tag(RealMobEffectTags.CANNOT_CLEAR).add(ModMobEffectIds.ECHOED);
    }
}
