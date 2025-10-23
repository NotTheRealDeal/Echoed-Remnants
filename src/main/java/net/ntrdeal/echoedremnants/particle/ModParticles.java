package net.ntrdeal.echoedremnants.particle;

import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

public class ModParticles {

    public static final SimpleParticleType ECHOED = register("echoed", new EchoedParticle(false));

    public static SimpleParticleType register(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(EchoedRemnants.MOD_ID, name), particleType);
    }

    public static void register() {}
}
