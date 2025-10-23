package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    @ModifyReturnValue(method = "hasBlindnessOrDarkness", at = @At("RETURN"))
    private boolean ntrdeal$echoed(boolean original, Camera camera) {
        return original || Functions.getEchoed(camera.getFocusedEntity());
    }
}
