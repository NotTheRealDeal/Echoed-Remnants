package net.ntrdeal.echoedremnants.reference;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.realapi.util.RegistryUtil;

public class ModParticleIds {
    private static final RegistryUtil.ResourceCreator<ParticleType<?>> CREATOR = RegistryUtil.resourceCreator(Registries.PARTICLE_TYPE, EchoedRemnants::id);

    public static final ResourceKey<ParticleType<?>> ECHOED = CREATOR.create("echoed");
}
