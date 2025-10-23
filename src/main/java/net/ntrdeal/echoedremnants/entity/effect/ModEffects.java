package net.ntrdeal.echoedremnants.entity.effect;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

public class ModEffects {

    public static final RegistryEntry<StatusEffect> ECHOED = register("echoed",
            new EchoedEffect(StatusEffectCategory.NEUTRAL, 0x034150).addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                    Identifier.of(EchoedRemnants.MOD_ID, "echoed"), -0.5f, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.PLAYER_BLOCK_BREAK_SPEED, Identifier.of(EchoedRemnants.MOD_ID, "echoed"),
                            -10, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static RegistryEntry<StatusEffect> register(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(EchoedRemnants.MOD_ID, name), statusEffect);
    }

    public static void register(){}
}
