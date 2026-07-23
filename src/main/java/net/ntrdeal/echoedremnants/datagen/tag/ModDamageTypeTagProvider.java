package net.ntrdeal.echoedremnants.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import net.ntrdeal.echoedremnants.reference.ModDamageTypeIds;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagProvider extends FabricTagsProvider<DamageType> {
    public ModDamageTypeTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, Registries.DAMAGE_TYPE, lookup);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        this.tag(DamageTypeTags.BYPASSES_ARMOR).add(ModDamageTypeIds.ECHOED);
        this.tag(DamageTypeTags.BYPASSES_EFFECTS).add(ModDamageTypeIds.ECHOED);
        this.tag(DamageTypeTags.BYPASSES_ENCHANTMENTS).add(ModDamageTypeIds.ECHOED);
        this.tag(DamageTypeTags.NO_IMPACT).add(ModDamageTypeIds.ECHOED);
    }
}
