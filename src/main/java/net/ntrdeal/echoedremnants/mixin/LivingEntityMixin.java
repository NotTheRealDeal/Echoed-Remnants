package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.component.ModComponents;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.component.EchoedFoodComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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
}
