package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.ntrdeal.echoedremnants.component.ModComponents;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import net.ntrdeal.echoedremnants.component.protection.ProtectionComponent;
import net.ntrdeal.realapi.cardinal_components.CardinalUtil;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Player.class)
public abstract class PlayerMixin extends Avatar {
    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level level) {
        super(type, level);
    }

    @WrapMethod(method = "isInvulnerableTo")
    private boolean ntrdeal$protected(ServerLevel level, DamageSource source, Operation<Boolean> original) {
        return original.call(level, source) || (source.is(DamageTypes.SONIC_BOOM) && CardinalUtil.returnOr(ModComponents.PROTECTION, this, ProtectionComponent::protectsEffect, false));
    }

    @WrapMethod(method = "getDestroySpeed")
    private float ntrdeal$echoed(BlockState state, Operation<Float> original) {
        return EchoedComponent.isEchoed(this) ? 0f : original.call(state);
    }
}
