package net.ntrdeal.echoedremnants.mixin.client;

import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.BlindnessFogEnvironment;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.ntrdeal.echoedremnants.client.EchoedFogEnvironment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(FogRenderer.class)
public abstract class FogRendererMixin {
    @Shadow @Final public static List<FogEnvironment> FOG_ENVIRONMENTS;

    static {
        for (int index = 0; index < FOG_ENVIRONMENTS.size(); index++) {
            if (!(FOG_ENVIRONMENTS.get(index) instanceof BlindnessFogEnvironment)) continue;
            FOG_ENVIRONMENTS.add(index, new EchoedFogEnvironment());
            break;
        }
    }
}
