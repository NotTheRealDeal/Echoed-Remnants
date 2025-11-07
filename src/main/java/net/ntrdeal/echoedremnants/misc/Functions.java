package net.ntrdeal.echoedremnants.misc;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
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

    public static boolean hasEcho(@Nullable Entity entity) {
        return entity instanceof LivingEntity livingEntity && ModComponents.ECHOED.get(livingEntity).echoed();
    }

    public static boolean hasVibrating(Entity entity) {
        return entity instanceof LivingEntity livingEntity && ModComponents.ECHOED.get(livingEntity).vibrating();
    }

    public static VoxelShape shouldPhase(VoxelShape shape, BlockView view, BlockPos pos, ShapeContext context) {
        if (!shape.isEmpty() && hasEcho(context instanceof EntityShapeContext entityContext ? entityContext.getEntity() : null)) {
            return !context.isDescending() && context.isAbove(shape, pos, true)  ? shape : VoxelShapes.empty();
        } else return shape;
    }

    @Environment(EnvType.CLIENT)
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

    @Environment(EnvType.CLIENT)
    public static RenderLayer transparentLayer(RenderLayer layer) {
        return modifiedParameters(layer, true, builder -> builder.transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY));
    }
}
