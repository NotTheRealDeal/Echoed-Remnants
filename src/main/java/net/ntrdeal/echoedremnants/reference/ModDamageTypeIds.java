package net.ntrdeal.echoedremnants.reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.realapi.util.RegistryUtil;

public class ModDamageTypeIds {
    private static final RegistryUtil.ResourceCreator<DamageType> CREATOR = RegistryUtil.resourceCreator(Registries.DAMAGE_TYPE, EchoedRemnants::id);

    public static final ResourceKey<DamageType> ECHOED = CREATOR.create("echoed");
}
