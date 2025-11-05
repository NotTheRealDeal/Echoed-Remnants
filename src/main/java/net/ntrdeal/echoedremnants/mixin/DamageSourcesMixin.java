package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.entity.damage.DamageSources;
import net.minecraft.registry.DynamicRegistryManager;
import net.ntrdeal.echoedremnants.entity.ModDamageSources;
import net.ntrdeal.echoedremnants.misc.ModDamageTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DamageSources.class)
public class DamageSourcesMixin implements ModDamageTypes {
    @Unique private ModDamageSources echoedSources;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void ntrdeal$addSources(DynamicRegistryManager registryManager, CallbackInfo ci) {
        this.echoedSources = new ModDamageSources((DamageSources) (Object) this);
    }

    @Override
    public ModDamageSources ntrdeal$echoedDamage() {
        return this.echoedSources;
    }
}
