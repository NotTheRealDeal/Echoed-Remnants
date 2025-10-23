package net.ntrdeal.echoedremnants;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.ntrdeal.echoedremnants.datagen.ModBlockTagProvider;
import net.ntrdeal.echoedremnants.datagen.ModEnglishProvider;
import net.ntrdeal.echoedremnants.datagen.ModModelProvider;
import net.ntrdeal.echoedremnants.datagen.ModRecipeProvider;

public class EchoedRemnantsDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModModelProvider::new);
		pack.addProvider(ModEnglishProvider::new);

		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModRecipeProvider::new);
	}
}
