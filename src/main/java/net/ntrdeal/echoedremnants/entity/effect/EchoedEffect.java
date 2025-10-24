package net.ntrdeal.echoedremnants.entity.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.ntrdeal.echoedremnants.misc.Functions;
import net.ntrdeal.echoedremnants.particle.ModParticles;

public class EchoedEffect extends StatusEffect {
    public EchoedEffect(StatusEffectCategory category, int color) {
        super(category, color, ModParticles.ECHOED);
    }

    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        return !Functions.wearingPendant(entity);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
