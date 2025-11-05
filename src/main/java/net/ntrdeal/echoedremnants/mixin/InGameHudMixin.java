package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.misc.Functions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Unique private static final Identifier ECHOED_FULL = Identifier.of(EchoedRemnants.MOD_ID, "hud/heart/echoed_heart_full");
    @Unique private static final Identifier ECHOED_HALF = Identifier.of(EchoedRemnants.MOD_ID, "hud/heart/echoed_heart_half");

    @WrapOperation(method = "drawHeart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V"))
    private void ntrdeal$renderEchoedHearts(DrawContext instance, Identifier texture, int x, int y, int width, int height, Operation<Void> original, @Local(argsOnly = true) InGameHud.HeartType type, @Local(argsOnly = true, ordinal = 1) boolean blinking, @Local(argsOnly = true, ordinal = 2) boolean half) {
        if (!blinking && !type.equals(InGameHud.HeartType.CONTAINER) && Functions.hasEcho(MinecraftClient.getInstance().getCameraEntity())) {
            original.call(instance, half ? ECHOED_HALF : ECHOED_FULL, x, y, 9, 9);
        } else original.call(instance, texture, x, y, width, height);
    }
}
