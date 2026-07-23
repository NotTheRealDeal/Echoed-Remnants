package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.ntrdeal.echoedremnants.block.ModBlocks;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SculkShriekerBlock.class)
public abstract class SculkShriekerBlockMixin extends BaseEntityBlock {
    protected SculkShriekerBlockMixin(Properties properties) {
        super(properties);
    }

    @WrapMethod(method = "createBlockStateDefinition")
    private void ntrdeal$addShards(StateDefinition.Builder<Block, BlockState> builder, Operation<Void> original) {
        original.call(builder.add(ModBlocks.SHARDS_PROPERTY));
    }

//    @WrapMethod(method = "stepOn")
//    private void ntrdeal$echoedCrafting(
//            Level level, BlockPos pos, BlockState onState,
//            Entity entity, Operation<Void> original
//    ) {
//        if (
//            level instanceof ServerLevel serverLevel && level.getBlockEntity(pos) instanceof ShriekerTriggerer triggerer && entity instanceof ItemEntity stackEntity &&
//            onState.getValue(SculkShriekerBlock.CAN_SUMMON) && !onState.getValue(SculkShriekerBlock.SHRIEKING) && onState.getValue(ModBlocks.SHARDS_PROPERTY) > 0
//        ) {
//            ItemStack stack = stackEntity.getItem();
//            EchoedFood food = stack.get(ModDataComponents.ECHOED_FOOD);
//            if (stack.has(DataComponents.CONSUMABLE) && (food == null || food.level() < 4) && triggerer.ntrdeal$tryShriek(serverLevel, entity)) {
//                ItemStack splitStack = stack.split(1);
//                food = EchoedFood.fromLevel(food == null ? 0 : food.level() + 1);
//                splitStack.set(ModDataComponents.ECHOED_FOOD, food);
//                Block.popResourceFromFace(level, pos, Direction.UP, splitStack);
//                stackEntity.setItem(stack);
//
//                level.playSound(
//                        null, pos, SoundEvents.SCULK_BLOCK_SPREAD,
//                        SoundSource.BLOCKS, 1f, 1f
//                );
//
//                if (level.getRandom().nextFloat() < food.level() * 0.1f) level.setBlock(pos, onState.setValue(
//                        ModBlocks.SHARDS_PROPERTY, onState.getValue(ModBlocks.SHARDS_PROPERTY) - 1
//                ), Block.UPDATE_CLIENTS);
//
//                super.stepOn(level, pos, onState, entity);
//                return;
//            }
//        }
//
//        original.call(level, pos, onState, entity);
//    }
}
