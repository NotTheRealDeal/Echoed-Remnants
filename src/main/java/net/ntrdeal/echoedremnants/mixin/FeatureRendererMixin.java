package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.entity.LivingEntity;
import net.ntrdeal.echoedremnants.EchoedRemnantsClient;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(FeatureRenderer.class)
public abstract class FeatureRendererMixin {
    @ModifyArg(method = "renderModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"))
    private static RenderLayer ntrdeal$echoedLayer(RenderLayer original, @Local(argsOnly = true) LivingEntity entity) {
        return original == null ? null : Functions.getEchoed(entity) ? EchoedRemnantsClient.ECHOED.getRenderLayer(Functions.transparentLayer(original)) : original;
    }
}
