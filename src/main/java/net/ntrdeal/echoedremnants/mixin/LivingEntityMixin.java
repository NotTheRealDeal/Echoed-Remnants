package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.misc.EchoedHolder;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable, EchoedHolder {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

//    @ModifyReturnValue(method = "isGlowing", at = @At("RETURN"))
//    private boolean addVibrating(boolean original) {
//        if (this.getWorld().isClient() && MinecraftClient.getInstance().getCameraEntity() instanceof LivingEntity entity) {
//            return original || (this.distanceTo(entity) <= 8f && Functions.hasVibrating(this) && Functions.wearingMonocle(entity));
//        } else return original;
//    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$pendant(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.SONIC_BOOM) && Functions.wearingPendant(this)) cir.setReturnValue(false);
    }

    @ModifyReturnValue(method = "canHaveStatusEffect", at = @At("RETURN"))
    private boolean ntrdeal$pendant(boolean original, StatusEffectInstance instance) {
        if (instance.equals(ModEffects.ECHOED)) {
            return !Functions.wearingPendant(this);
        } else return original;
    }
}
