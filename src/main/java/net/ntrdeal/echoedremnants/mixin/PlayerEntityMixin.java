package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

//    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
//    private void ntrdeal$addEvent(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> cir) {
//        if (stack.get(ModDataComponents.ECHOED_FOOD) instanceof EchoedFoodComponent component && !Functions.wearingPendant(this)) {
//            world.playSound(
//                    null,
//                    this.getX(),
//                    this.getY(),
//                    this.getZ(),
//                    SoundEvents.BLOCK_FIRE_EXTINGUISH,
//                    SoundCategory.NEUTRAL,
//                    1.0F,
//                    1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F
//            );
//
//            SyncedEchoedComponent echoedComponent = ModComponents.ECHOED.get(this);
//            echoedComponent.setFromComponent(component);
//
//            stack.decrementUnlessCreative(1, this);
//            this.emitGameEvent(GameEvent.EAT);
//            cir.setReturnValue(stack);
//        }
//    }
}