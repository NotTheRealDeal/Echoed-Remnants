package net.ntrdeal.echoedremnants.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record EchoedFoodComponent(int delay, int duration, int shrieks) {
    public static final EchoedFoodComponent DEFAULT = new EchoedFoodComponent(0, 600, 0);

    public static final Codec<EchoedFoodComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("delay", 0).forGetter(EchoedFoodComponent::delay),
            Codec.INT.optionalFieldOf("duration", 600).forGetter(EchoedFoodComponent::duration),
            Codec.INT.optionalFieldOf("shrieks", 0).forGetter(EchoedFoodComponent::shrieks)
    ).apply(instance, EchoedFoodComponent::new));

    public static final PacketCodec<RegistryByteBuf, EchoedFoodComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            EchoedFoodComponent::delay,
            PacketCodecs.INTEGER,
            EchoedFoodComponent::duration,
            PacketCodecs.INTEGER,
            EchoedFoodComponent::shrieks,
            EchoedFoodComponent::new
    );

    public EchoedFoodComponent levelUp() {
        return new EchoedFoodComponent((int) Math.round(Math.pow(2, shrieks()))*100, (int) Math.round(Math.pow(2, shrieks()+1))*600, shrieks()+1);
    }
}
