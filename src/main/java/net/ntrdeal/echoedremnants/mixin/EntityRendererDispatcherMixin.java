package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.Entity;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderDispatcher.class)
public class EntityRendererDispatcherMixin {
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;isInvisible()Z"))
    private boolean ntrdeal$echoed(Entity instance) {
        return instance.isInvisible() || Functions.getEchoed(instance);
    }
}
