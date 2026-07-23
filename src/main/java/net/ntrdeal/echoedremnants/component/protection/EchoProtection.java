package net.ntrdeal.echoedremnants.component.protection;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.ntrdeal.realapi.util.FlagUtil;

public record EchoProtection(short protectedFrequencies, boolean protectsEffect, boolean protectsEcholocation) {
    public static final EchoProtection EMPTY = of(true, true, 0, 1, 2, 3, 4, 5 ,6, 7, 8, 14, 15);
    public static final Codec<EchoProtection> CODEC = RecordCodecBuilder.create(protection -> protection.group(
            Codec.SHORT.optionalFieldOf("protected_frequencies", EMPTY.protectedFrequencies()).forGetter(EchoProtection::protectedFrequencies),
            Codec.BOOL.optionalFieldOf("protects_effect", EMPTY.protectsEffect()).forGetter(EchoProtection::protectsEffect),
            Codec.BOOL.optionalFieldOf("protects_echolocation", EMPTY.protectsEcholocation()).forGetter(EchoProtection::protectsEcholocation)
    ).apply(protection, EchoProtection::new));
    public static final StreamCodec<FriendlyByteBuf, EchoProtection> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.SHORT, EchoProtection::protectedFrequencies,
            ByteBufCodecs.BOOL, EchoProtection::protectsEffect,
            ByteBufCodecs.BOOL, EchoProtection::protectsEcholocation,
            EchoProtection::new
    );

    public static EchoProtection of(boolean protectsEffect, boolean protectsEcholocation, int... frequencies) {
        short protectedFrequencies = 0;
        for (int frequency : frequencies) protectedFrequencies = FlagUtil.set(protectedFrequencies, frequency);
        return new EchoProtection(protectedFrequencies, protectsEffect, protectsEcholocation);
    }

    public boolean isProtected(Holder<GameEvent> holder) {
        return FlagUtil.isSet(this.protectedFrequencies, VibrationSystem.getGameEventFrequency(holder));
    }
}
