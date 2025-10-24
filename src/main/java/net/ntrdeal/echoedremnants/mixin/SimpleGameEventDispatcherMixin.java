package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.world.event.listener.SimpleGameEventDispatcher;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SimpleGameEventDispatcher.class)
public class SimpleGameEventDispatcherMixin {
//    @Inject(method = "dispatch", at = @At("HEAD"), cancellable = true)
//    private void ntrdeal$pendant(RegistryEntry<GameEvent> event, Vec3d pos, GameEvent.Emitter emitter, GameEventDispatcher.DispatchCallback callback, CallbackInfoReturnable<Boolean> cir) {
//        int frequency = Vibrations.getFrequency(event);
//        boolean entityEvent = (frequency <= 8 || frequency >= 14);
//
//        if (entityEvent && emitter.sourceEntity() instanceof LivingEntity entity) {
//            if (Functions.wearingPendant(entity)) cir.setReturnValue(false);
//            else ModComponents.ECHOED.get(entity).setVibratingTicks(50);
//        }
//    }
}
