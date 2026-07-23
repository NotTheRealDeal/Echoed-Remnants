package net.ntrdeal.echoedremnants.mixin.client;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.gui.Hud;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Hud.HeartType.class)
public enum HeartTypeMixin {
    ECHOEDREMNANTS_ECHOED(
            EchoedRemnants.id("hud/heart/echoed_full"),
            Identifier.withDefaultNamespace("hud/heart/full_blinking"),
            EchoedRemnants.id("hud/heart/echoed_half"),
            Identifier.withDefaultNamespace("hud/heart/half_blinking"),
            EchoedRemnants.id("hud/heart/echoed_full"),
            Identifier.withDefaultNamespace("hud/heart/hardcore_full_blinking"),
            EchoedRemnants.id("hud/heart/echoed_half"),
            Identifier.withDefaultNamespace("hud/heart/hardcore_half_blinking")
    );

    @Shadow
    HeartTypeMixin(
            final Identifier full, final Identifier fullBlinking, final Identifier half, final Identifier halfBlinking,
            final Identifier hardcoreFull, final Identifier hardcoreFullBlinking, final Identifier hardcoreHalf, final Identifier hardcoreHalfBlinking
    ) {}

    @WrapMethod(method = "forPlayer")
    private static Hud.HeartType ntrdeal$echoed(Player player, Operation<Hud.HeartType> original) {
        return EchoedComponent.isEchoed(player) ? (Hud.HeartType)(Object)ECHOEDREMNANTS_ECHOED : original.call(player);
    }
}
