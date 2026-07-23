package net.ntrdeal.echoedremnants.reference;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.realapi.util.RegistryUtil;

public class ModMobEffectIds {
    private static final RegistryUtil.ResourceCreator<MobEffect> CREATOR = RegistryUtil.resourceCreator(Registries.MOB_EFFECT, EchoedRemnants::id);

    public static final ResourceKey<MobEffect> ECHOED = CREATOR.create("echoed");
}
