package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.component.ModComponents;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.component.EchoedFoodComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import net.ntrdeal.echoedremnants.misc.Functions;
import net.ntrdeal.echoedremnants.misc.ModDamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract float getHealth();

    @Shadow public abstract boolean isDead();

    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$pendant(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (source.isOf(DamageTypes.SONIC_BOOM) && Functions.wearingPendant(this)) cir.setReturnValue(false);
    }

    @Inject(method = "eatFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;applyFoodEffects(Lnet/minecraft/component/type/FoodComponent;)V"))
    private void ntrdeal$addEchoed(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (!Functions.wearingPendant(this) && stack.get(ModDataComponents.ECHOED_FOOD) instanceof EchoedFoodComponent component) {
            ModComponents.ECHOED.get(this).setFromComponent(component);
        }
    }

    @ModifyReturnValue(method = "isGlowing", at = @At("RETURN"))
    private boolean ntrdeal$vibrating(boolean original) {
        if (this.getWorld().isClient()) {
            boolean glow = MinecraftClient.getInstance().getCameraEntity() instanceof LivingEntity entity && this.distanceTo(entity) <= 8f && Functions.wearingMonocle(entity) && Functions.hasVibrating(this);
            return original || glow;
        } else return original;
    }

    @WrapMethod(method = "heal")
    private void ntrdeal$preventHealing(float amount, Operation<Void> original) {
        if (!Functions.hasEcho(this)) original.call(amount);
    }

    @WrapOperation(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private boolean ntrdeal$keepEchoed(LivingEntity entity, Operation<Boolean> original) {
        if (entity.getStatusEffect(ModEffects.ECHOED) instanceof StatusEffectInstance instance) {
            int duration = instance.getDuration();
            boolean bool = original.call(entity);
            entity.addStatusEffect(new StatusEffectInstance(ModEffects.ECHOED, duration));
            return bool;
        } else return original.call(entity);
    }

    @WrapOperation(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0))
    private boolean ntrdeal$modifiedSuffocation(LivingEntity entity, DamageSource source, float amount, Operation<Boolean> original) {
        if (Functions.hasEcho(this)) {
            float f = this.getDimensions(this.getPose()).width() * 0.8F;
            boolean insideSculk = BlockPos.stream(Box.of(this.getEyePos(), f, 1.0E-6, f)).map(this.getWorld()::getBlockState).filter(state -> !state.isAir()).allMatch(state -> state.isOf(Blocks.SCULK));

            if (insideSculk) {
                if (!this.isDead()) {
                    this.setHealth(this.getHealth() + 0.1f);
                    return true;
                } else return false;
            } else return original.call(entity, ((ModDamageTypes)this.getDamageSources()).ntrdeal$echoedDamage().echoedWall(), 0.5f);
        } else return original.call(entity, source, amount);
    }
}