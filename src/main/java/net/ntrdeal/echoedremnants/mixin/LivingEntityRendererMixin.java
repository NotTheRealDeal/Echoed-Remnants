package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.ntrdeal.echoedremnants.EchoedRemnantsClient;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> {
    @ModifyReturnValue(method = "getRenderLayer", at = @At("RETURN"))
    private RenderLayer ntrdeal$glowing(@Nullable RenderLayer original, T entity) {
        return original == null ? null : Functions.getEchoed(entity) ? EchoedRemnantsClient.ECHOED.getRenderLayer(Functions.transparentLayer(original)) : original;
    }
}
