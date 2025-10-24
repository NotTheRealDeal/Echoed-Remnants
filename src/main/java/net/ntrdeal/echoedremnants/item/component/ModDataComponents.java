package net.ntrdeal.echoedremnants.item.component;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

import java.util.function.UnaryOperator;

public class ModDataComponents {

    public static final ComponentType<EchoedFoodComponent> ECHOED_FOOD = register("echoed_food", builder ->
            builder.codec(EchoedFoodComponent.CODEC).packetCodec(EchoedFoodComponent.PACKET_CODEC));

    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(EchoedRemnants.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void register(){}
}
