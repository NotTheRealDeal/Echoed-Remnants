package net.ntrdeal.echoedremnants.entity;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

public class ModDamageSources {
    public static final RegistryKey<DamageType> ECHOED_WALL = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(EchoedRemnants.MOD_ID, "echoed_wall"));

    private final DamageSource echoedWall;

    public ModDamageSources(DamageSources sources) {
        this.echoedWall = sources.create(ECHOED_WALL);
    }

    public DamageSource echoedWall() {
        return this.echoedWall;
    }
}
