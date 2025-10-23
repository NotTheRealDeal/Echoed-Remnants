package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.ModItems;
import net.ntrdeal.echoedremnants.item.component.EchoedComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Unique
    private int echoedTicks = 0;

    @Unique
    private int duration = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void ntrdeal$addData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("echoedTicks", this.echoedTicks);
        nbt.putInt("echoedDuration", this.duration);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void ntrdeal$readData(NbtCompound nbt, CallbackInfo ci) {
        this.echoedTicks = nbt.getInt("echoedTicks");
        this.duration = nbt.getInt("echoedDuration");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void ntrdeal$tickEchoed(CallbackInfo ci) {
        if (this.echoedTicks > 0) {
            if (this.echoedTicks == 1) {
                ntrdeal$echo();
            }
            this.echoedTicks--;
        }
    }

    @Override
    public void onEquipStack(EquipmentSlot slot, ItemStack oldStack, ItemStack newStack) {
        if (newStack.isOf(ModItems.PENDANT) && slot == EquipmentSlot.CHEST) {
            if (this.echoedTicks > 0) this.echoedTicks = 0;
            if (Functions.hasEcho(this)) this.removeStatusEffect(ModEffects.ECHOED);
        }
        super.onEquipStack(slot, oldStack, newStack);
    }

    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$addEvent(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.get(ModDataComponents.ECHOED_FOOD) instanceof EchoedComponent component && !Functions.wearingPendant(this)) {
            world.playSound(
                    null,
                    this.getX(),
                    this.getY(),
                    this.getZ(),
                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
                    SoundCategory.NEUTRAL,
                    1.0F,
                    1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F
            );

            if (this.echoedTicks <= 0) {
                this.duration = component.duration();
                if (component.delay() <= 0) {
                    ntrdeal$echo();
                } else this.echoedTicks = component.delay();
            }

            stack.decrementUnlessCreative(1, this);
            this.emitGameEvent(GameEvent.EAT);
            cir.setReturnValue(stack);
        }
    }

    @Unique
    private void ntrdeal$echo() {
        addStatusEffect(new StatusEffectInstance(ModEffects.ECHOED, this.duration));
        this.duration = 0;
    }
}
