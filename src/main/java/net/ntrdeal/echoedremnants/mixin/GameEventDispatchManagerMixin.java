package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.GameEventTags;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventDispatchManager;
import net.ntrdeal.echoedremnants.component.ModComponents;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameEventDispatchManager.class)
public class GameEventDispatchManagerMixin {
    @Inject(method = "dispatch", at = @At("HEAD"), cancellable = true)
    private void ntrdeal$pendant(RegistryEntry<GameEvent> event, Vec3d emitterPos, GameEvent.Emitter emitter, CallbackInfo ci) {
        int frequency = Vibrations.getFrequency(event);
        boolean entityEvent = (frequency <= 8 || frequency >= 14);

        if (entityEvent && emitter.sourceEntity() instanceof LivingEntity entity) {
            if (Functions.wearingPendant(entity)) ci.cancel();
            else if (!cantAccept(event, emitter)) ModComponents.ECHOED.get(entity).setVibratingTicks(50);
        }
    }

    @Unique
    private static boolean cantAccept(RegistryEntry<GameEvent> gameEvent, GameEvent.Emitter emitter) {
        return (emitter.sourceEntity() instanceof Entity entity && entity.bypassesSteppingEffects() && gameEvent.isIn(GameEventTags.IGNORE_VIBRATIONS_SNEAKING)) || (emitter.affectedState() == null || emitter.affectedState().isIn(BlockTags.DAMPENS_VIBRATIONS));
    }
}
