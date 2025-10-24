package net.ntrdeal.echoedremnants.client;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.misc.Functions;

public class EchoedFogModifier implements BackgroundRenderer.StatusEffectFogModifier {
    @Override
    public RegistryEntry<StatusEffect> getStatusEffect() {
        return ModEffects.ECHOED;
    }

    @Override
    public boolean shouldApply(LivingEntity entity, float tickDelta) {
        return BackgroundRenderer.StatusEffectFogModifier.super.shouldApply(entity, tickDelta) && !Functions.wearingPendant(entity);
    }

    @Override
    public void applyStartEndModifier(BackgroundRenderer.FogData fogData, LivingEntity entity, StatusEffectInstance effect, float viewDistance, float tickDelta) {
        float f = MathHelper.lerp(effect.getFadeFactor(entity, tickDelta), viewDistance, 15.0F);
        fogData.fogStart = fogData.fogType == BackgroundRenderer.FogType.FOG_SKY ? 0.0F : f * 0.75F;
        fogData.fogEnd = f;
    }

    @Override
    public float applyColorModifier(LivingEntity entity, StatusEffectInstance effect, float f, float tickDelta) {
        return 1.0F - effect.getFadeFactor(entity, tickDelta);
    }
}
