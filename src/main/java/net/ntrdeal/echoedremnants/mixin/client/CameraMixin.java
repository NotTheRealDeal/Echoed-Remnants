package net.ntrdeal.echoedremnants.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Camera;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @WrapOperation(method = "extractRenderState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 0))
    private boolean ntrdeal$orIsEchoed(LivingEntity entity, Holder<MobEffect> effect, Operation<Boolean> original) {
        return original.call(entity, effect) || EchoedComponent.isEchoed(entity);
    }
}
