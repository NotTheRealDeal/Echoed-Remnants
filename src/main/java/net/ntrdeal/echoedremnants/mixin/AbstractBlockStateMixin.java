package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.TypedInstance;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.ntrdeal.echoedremnants.block.ModBlockTags;
import net.ntrdeal.echoedremnants.component.echoed.EchoedComponent;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin implements TypedInstance<Block> {
    @WrapMethod(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;")
    private VoxelShape ntrdeal$echoPhysical(BlockGetter level, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        VoxelShape shape = original.call(level, pos, context);
        if (!(context instanceof EntityCollisionContext entityContext) || !EchoedComponent.isEchoed(entityContext.getEntity())) return shape;
        boolean canEcho = false; try {canEcho = !this.is(ModBlockTags.CANNOT_ECHO);} catch (IllegalStateException ignore) {}
        return (context.isDescending() || !context.isAbove(shape, pos, true)) && canEcho ? Shapes.empty() : shape;
    }

    @WrapMethod(method = "getVisualShape")
    private VoxelShape ntrdeal$echoVisual(BlockGetter level, BlockPos pos, CollisionContext context, Operation<VoxelShape> original) {
        VoxelShape shape = original.call(level, pos, context);
        if (!(context instanceof EntityCollisionContext entityContext) || !EchoedComponent.isEchoed(entityContext.getEntity())) return shape;
        boolean canEcho = false; try {canEcho = !this.is(ModBlockTags.CANNOT_ECHO);} catch (IllegalStateException ignore) {}
        return canEcho ? Shapes.empty() : shape;
    }
}
