package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.MilkBucketItem;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MilkBucketItem.class)
public class MilkBucketItemMixin {
    @WrapOperation(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean ntrdeal$keepEchoed(LivingEntity entity, Operation<Boolean> original) {
        if (entity.getStatusEffect(ModEffects.ECHOED) instanceof StatusEffectInstance instance) {
            int duration = instance.getDuration();
            boolean bool = original.call(entity);
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.ECHOED, duration));
            return bool;
        } else return original.call(entity);
    }
}
