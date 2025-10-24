package net.ntrdeal.echoedremnants.component;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<SyncedEchoedComponent> ECHOED = ComponentRegistry.getOrCreate(Identifier.of(EchoedRemnants.MOD_ID, "echoed"), SyncedEchoedComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, ECHOED, SyncedEchoedComponent::new);
    }
}
