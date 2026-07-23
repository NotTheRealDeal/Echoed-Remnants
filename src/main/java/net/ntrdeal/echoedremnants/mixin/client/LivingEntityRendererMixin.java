package net.ntrdeal.echoedremnants.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin<T extends LivingEntity,S extends LivingEntityRenderState> {
    @Unique private static final RenderStateDataKey<Boolean> ECHOED_STATE = RenderStateDataKey.create();

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void ntrdeal$echoedTint(T entity, S state, float partialTicks, CallbackInfo ci) {
        state.setData(ECHOED_STATE, EchoedComponent.isEchoed(entity));
    }

    @WrapMethod(method = "getModelTint")
    private int ntrdeal$echoedTint(S state, Operation<Integer> original) {
        return state.getDataOrDefault(ECHOED_STATE, false) ? 0x7fffffff : original.call(state);
    }
}
