package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.fabric.api.attachment.v1.AttachmentTarget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracked;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.event.GameEvent;
import net.ntrdeal.echoedremnants.misc.EchoedHolder;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract World getWorld();

    @Shadow public abstract float distanceTo(Entity entity);

    @WrapMethod(method = "pushOutOfBlocks")
    private void ntrdeal$echoed(double x, double y, double z, Operation<Void> original) {
        if (!Functions.getEchoed((Entity)(Object)this)) {
            original.call(x, y, z);
        }
    }

    @ModifyReturnValue(method = "isGlowing", at = @At("RETURN"))
    private boolean ntrdeal$addVibrated(boolean original) {
        return original || vibrationGlow();
    }

    @Unique private boolean vibrationGlow() {
        if (this.getWorld().isClient() && MinecraftClient.getInstance().getCameraEntity() instanceof Entity entity) {
            return this.distanceTo(entity) <= 12f;
        }
        return false;
//        if (this.getWorld().isClient() && Functions.wearingMonocle(MinecraftClient.getInstance().getCameraEntity()) && this instanceof EchoedHolder holder) {
//            System.out.println("wowies" + holder.ntrdeal$getEchoed());
//            return holder.ntrdeal$getEchoed();
//        } else return false;
    }

    @Inject(method = "emitGameEvent(Lnet/minecraft/registry/entry/RegistryEntry;Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void ntrdeal$vibrated(RegistryEntry<GameEvent> event, @Nullable Entity entity, CallbackInfo ci) {

    }
}
