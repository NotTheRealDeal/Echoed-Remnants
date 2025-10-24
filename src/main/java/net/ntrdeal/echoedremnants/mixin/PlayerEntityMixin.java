package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.ntrdeal.echoedremnants.component.ModComponents;
import net.ntrdeal.echoedremnants.component.SyncedEchoedComponent;
import net.ntrdeal.echoedremnants.item.component.EchoedFoodComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$addEvent(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        if (stack.get(ModDataComponents.ECHOED_FOOD) instanceof EchoedFoodComponent component && !Functions.wearingPendant(this)) {
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

            SyncedEchoedComponent echoedComponent = ModComponents.ECHOED.get(this);
            echoedComponent.setFromComponent(component);

            stack.decrementUnlessCreative(1, this);
            this.emitGameEvent(GameEvent.EAT);
            cir.setReturnValue(stack);
        }
    }
}