package net.ntrdeal.echoedremnants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.block.entity.renderer.SculkShriekerBlockEntityRenderer;
import net.ntrdeal.echoedremnants.misc.Functions;
import net.ntrdeal.echoedremnants.particle.ModParticles;
import org.ladysnake.satin.api.event.EntitiesPreRenderCallback;
import org.ladysnake.satin.api.event.ShaderEffectRenderCallback;
import org.ladysnake.satin.api.managed.ManagedCoreShader;
import org.ladysnake.satin.api.managed.ManagedShaderEffect;
import org.ladysnake.satin.api.managed.ShaderEffectManager;
import org.ladysnake.satin.api.managed.uniform.Uniform1f;

public class EchoedRemnantsClient implements ClientModInitializer {

    public static final ManagedShaderEffect ECHOED_SHADER = ShaderEffectManager.getInstance().manage(Identifier.of(EchoedRemnants.MOD_ID, "shaders/post/echoed.json"));
    public static final ManagedCoreShader ECHOED = ShaderEffectManager.getInstance().manageCoreShader(Identifier.of(EchoedRemnants.MOD_ID, "echoed"));


    @Override
    public void onInitializeClient() {
        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (Functions.getEchoed(MinecraftClient.getInstance().getCameraEntity())) {
                ECHOED_SHADER.render(tickDelta);
            }
        });

        ParticleFactoryRegistry.getInstance().register(ModParticles.ECHOED, SpellParticle.DefaultFactory::new);
        BlockEntityRendererFactories.register(BlockEntityType.SCULK_SHRIEKER, SculkShriekerBlockEntityRenderer::new);
    }
}