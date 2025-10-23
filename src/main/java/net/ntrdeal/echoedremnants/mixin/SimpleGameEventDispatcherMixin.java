package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventDispatcher;
import net.minecraft.world.event.listener.SimpleGameEventDispatcher;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleGameEventDispatcher.class)
public class SimpleGameEventDispatcherMixin {
    @Inject(method = "dispatch", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$pendant(RegistryEntry<GameEvent> event, Vec3d pos, GameEvent.Emitter emitter, GameEventDispatcher.DispatchCallback callback, CallbackInfoReturnable<Boolean> cir) {
        int frequency = Vibrations.getFrequency(event);
        if ((frequency <= 8 || frequency >= 14) && Functions.wearingPendant(emitter.sourceEntity())) {
            cir.setReturnValue(false);
        }
    }
}
