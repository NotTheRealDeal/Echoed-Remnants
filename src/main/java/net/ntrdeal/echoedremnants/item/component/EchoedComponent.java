package net.ntrdeal.echoedremnants.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record EchoedComponent(int delay, int duration, int shrieks) {
    public static final EchoedComponent DEFAULT = new EchoedComponent(0, 600, 0);

    public static final Codec<EchoedComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("delay", 0).forGetter(EchoedComponent::delay),
            Codec.INT.optionalFieldOf("duration", 600).forGetter(EchoedComponent::duration),
            Codec.INT.optionalFieldOf("shrieks", 0).forGetter(EchoedComponent::shrieks)
    ).apply(instance, EchoedComponent::new));

    public static final PacketCodec<RegistryByteBuf, EchoedComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            EchoedComponent::delay,
            PacketCodecs.INTEGER,
            EchoedComponent::duration,
            PacketCodecs.INTEGER,
            EchoedComponent::shrieks,
            EchoedComponent::new
    );

    public EchoedComponent levelUp() {
        return new EchoedComponent((int) Math.round(Math.pow(2, shrieks()))*100, (int) Math.round(Math.pow(2, shrieks()+1))*600, shrieks()+1);
    }
}
