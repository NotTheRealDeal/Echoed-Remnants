package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.ntrdeal.echoedremnants.client.EchoedFogModifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {
    @Shadow @Final private static List<BackgroundRenderer.StatusEffectFogModifier> FOG_MODIFIERS;

    static {
        FOG_MODIFIERS.add(new EchoedFogModifier());
    }
}
