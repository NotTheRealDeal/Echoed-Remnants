package net.ntrdeal.echoedremnants.misc;

import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.ntrdeal.echoedremnants.component.ModComponents;
import org.jetbrains.annotations.Nullable;
import org.ladysnake.satin.impl.RenderLayerDuplicator;

import java.util.function.Consumer;

public class Functions {
    public static boolean wearingPendant(Entity entity) {
        return ModComponents.ECHOED.get(entity).wearingPendant();
    }

    public static boolean wearingMonocle(Entity entity) {
        return entity instanceof LivingEntity livingEntity && ModComponents.ECHOED.get(livingEntity).wearingMonocle();
    }

    public static boolean hasEcho(Entity entity) {
        return entity instanceof LivingEntity livingEntity && ModComponents.ECHOED.get(livingEntity).echoed();
    }

    public static boolean hasVibrating(Entity entity) {
        return entity instanceof LivingEntity livingEntity && ModComponents.ECHOED.get(livingEntity).vibrating();
    }

    public static boolean shouldPhase(Entity holder, VoxelShape shape, BlockPos pos, ShapeContext context) {
        if (holder instanceof LivingEntity entity && hasEcho(entity)) {
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
