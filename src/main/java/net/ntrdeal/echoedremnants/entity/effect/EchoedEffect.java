package net.ntrdeal.echoedremnants.entity.effect;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.ntrdeal.echoedremnants.particle.ModParticles;

public class EchoedEffect extends StatusEffect {
    public EchoedEffect(StatusEffectCategory category, int color) {
        super(category, color, ModParticles.ECHOED);
    }
}
