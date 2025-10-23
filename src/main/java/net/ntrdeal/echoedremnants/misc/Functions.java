package net.ntrdeal.echoedremnants.misc;

import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.entity.effect.ModEffects;
import net.ntrdeal.echoedremnants.item.ModItems;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.satin.impl.RenderLayerDuplicator;

import java.util.List;
import java.util.function.Consumer;

public class Functions {
    public static boolean wearingPendant(@Nullable Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.PENDANT);
        } else return false;
    }

    public static boolean wearingMonocle(@Nullable Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(ModItems.MONOCLE);
        } else return false;
    }

    public static boolean hasEcho(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            return livingEntity.hasStatusEffect(ModEffects.ECHOED);
        } else return false;
    }

    public static boolean getEchoed(Entity entity) {
        if (entity instanceof EchoedHolder holder) {
            return holder.ntrdeal$getEchoed();
        } else return false;
    }

    public static boolean shouldPhase(Entity holder, VoxelShape shape, BlockPos pos, ShapeContext context) {
        if (getEchoed(holder)) {
            return (holder.getY() < (double) pos.getY() + shape.getMax(Direction.Axis.Y) - (holder.isOnGround() ? 8.05 / 16.0 : 0.0015)) || context.isDescending();
        } else return false;
    }

    public static RenderLayer modifiedParameters(RenderLayer layer, @Nullable Boolean transparent, Consumer<RenderLayer.MultiPhaseParameters.Builder> modifiers) {
        RenderLayerDuplicator.SatinRenderLayer satinLayer = (RenderLayerDuplicator.SatinRenderLayer) layer;
        return RenderLayer.of(
                layer.toString(),
                layer.getVertexFormat(),
                layer.getDrawMode(),
                layer.getExpectedBufferSize(),
                layer.hasCrumbling(),
                transparent == null ? layer.isTranslucent() : transparent,
                satinLayer.satin$copyPhaseParameters(modifiers)
        );
    }

    public static RenderLayer transparentLayer(RenderLayer layer) {
        return modifiedParameters(layer, true, builder -> builder.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY));
    }
}
