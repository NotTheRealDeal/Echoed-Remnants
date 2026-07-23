package net.ntrdeal.echoedremnants;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpellParticle;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.BlockEntityTypes;
import net.ntrdeal.echoedremnants.client.SculkShriekerRenderer;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import net.ntrdeal.echoedremnants.component.echoed.EchoedEffect;
import net.ntrdeal.realapi.client.event.PostShaderEvent;

public class EchoedRemnantsClient implements ClientModInitializer {
    public static final Identifier ECHOED_POST_SHADER = EchoedRemnants.id("echoed");

    @Override
    public void onInitializeClient() {
        ParticleProviderRegistry.getInstance().register(EchoedEffect.PARTICLE, SpellParticle.Provider::new);
        BlockEntityRenderers.register(BlockEntityTypes.SCULK_SHRIEKER, SculkShriekerRenderer::new);

        PostShaderEvent.EVENT.register((_, manager, target, pool) -> {
            if (!EchoedComponent.isEchoed(Minecraft.getInstance().getCameraEntity())) return;
            PostShaderEvent.renderPostMain(manager.getPostChain(ECHOED_POST_SHADER, LevelTargetBundle.MAIN_TARGETS), target, pool);
        });
    }
}
