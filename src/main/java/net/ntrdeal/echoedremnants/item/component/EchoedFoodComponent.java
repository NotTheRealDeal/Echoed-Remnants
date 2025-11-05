package net.ntrdeal.echoedremnants.item.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public record EchoedFoodComponent(int delay, int duration, int shrieks) implements TooltipAppender {
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

    public static EchoedFoodComponent levelUp(@Nullable EchoedFoodComponent component) {
        if (component != null) return new EchoedFoodComponent(
                (int) Math.round(Math.pow(2, component.shrieks()))*100,
                (int) Math.round(Math.pow(2, component.shrieks()+1))*600,
                component.shrieks() + 1
        ); else return DEFAULT;
    }

    public StatusEffectInstance get() {
        return new StatusEffectInstance(ModEffects.ECHOED, this.duration);
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        tooltip.accept(Text.literal("Duration: %s | Delay: %s".formatted(StatusEffectUtil.getDurationText(get(), 1.0f, context.getUpdateTickRate()).getString(), (AttributeModifiersComponent.DECIMAL_FORMAT.format(this.delay / 20d))+"s")).formatted(Formatting.BLUE));
    }
}
