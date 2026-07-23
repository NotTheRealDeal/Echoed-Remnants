package net.ntrdeal.echoedremnants.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {
    @WrapMethod(method = "getViewBlockingState")
    private static BlockState ntrdeal$echoedRendering(Player player, Operation<BlockState> original) {
        return EchoedComponent.isEchoed(player) ? null : original.call(player);
    }
}
