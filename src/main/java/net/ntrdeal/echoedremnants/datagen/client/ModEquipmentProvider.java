package net.ntrdeal.echoedremnants.datagen.client;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.ntrdeal.echoedremnants.item.equipment.ModEquipmentAssets;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class ModEquipmentProvider extends FabricCodecDataProvider<EquipmentClientInfo> {
    public ModEquipmentProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup, PackOutput.Target.RESOURCE_PACK, "equipment", EquipmentClientInfo.CODEC);
    }

    @Override
    protected void configure(BiConsumer<Identifier, EquipmentClientInfo> provider, HolderLookup.Provider lookup) {
        provider.accept(ModEquipmentAssets.ROSE_GOLD.identifier(), EquipmentClientInfo.builder().addLayers(
                EquipmentClientInfo.LayerType.HUMANOID,
                EquipmentClientInfo.Layer.leatherDyeable(ModEquipmentAssets.ROSE_GOLD.identifier(), false)
            ).build()
        );
    }

    @Override public String getName() {return "equipment";}
}
