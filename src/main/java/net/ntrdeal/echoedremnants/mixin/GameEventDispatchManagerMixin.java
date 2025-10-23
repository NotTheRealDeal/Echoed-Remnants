package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventDispatchManager;
import net.ntrdeal.echoedremnants.misc.EchoedHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEventDispatchManager.class)
public class GameEventDispatchManagerMixin {
    @Inject(method = "dispatch", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/DebugInfoSender;sendGameEvent(Lnet/minecraft/world/World;Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/util/math/Vec3d;)V"))
    private void ntrdeal$vibrated(RegistryEntry<GameEvent> event, Vec3d emitterPos, GameEvent.Emitter emitter, CallbackInfo ci) {
        int frequency = Vibrations.getFrequency(event);
        if ((frequency <= 8 || frequency >= 14) && emitter.sourceEntity() instanceof EchoedHolder holder) {
            holder.ntrdeal$setVibratedTicks(50);
        }
    }
}
