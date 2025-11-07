package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;


@Environment(EnvType.CLIENT) @Mixin(LivingEntity.class)
public abstract class LivingEntityMixinClient extends Entity implements Attackable {
    public LivingEntityMixinClient(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "isGlowing", at = @At("RETURN"))
    private boolean ntrdeal$vibrating(boolean original) {
        boolean glow = MinecraftClient.getInstance().getCameraEntity() instanceof LivingEntity entity && this.distanceTo(entity) <= 8f && Functions.wearingMonocle(entity) && Functions.hasVibrating(this);
        return original || glow;
    }
}
