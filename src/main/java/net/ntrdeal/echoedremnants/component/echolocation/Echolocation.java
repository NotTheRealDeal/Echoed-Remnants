package net.ntrdeal.echoedremnants.component.echolocation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record Echolocation(int range, int duration, boolean bypassProtection) {
    public static final Echolocation EMPTY = new Echolocation(10, 50, false);
    public static final Codec<Echolocation> CODEC = RecordCodecBuilder.create(echolocation -> echolocation.group(
            Codec.INT.optionalFieldOf("range", EMPTY.range()).forGetter(Echolocation::range),
            Codec.INT.optionalFieldOf("duration", EMPTY.duration()).forGetter(Echolocation::duration),
            Codec.BOOL.optionalFieldOf("bypass_protection", EMPTY.bypassProtection()).forGetter(Echolocation::bypassProtection)
    ).apply(echolocation, Echolocation::new));
    public static final StreamCodec<FriendlyByteBuf, Echolocation> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT, Echolocation::range,
            ByteBufCodecs.VAR_INT, Echolocation::duration,
            ByteBufCodecs.BOOL, Echolocation::bypassProtection,
            Echolocation::new
    );

    public boolean isActive() {
        return this.range > 0 && this.duration > 0;
    }

    public int rangeSqr() {
        return this.range * this.range;
    }
}
