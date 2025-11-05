package net.ntrdeal.echoedremnants.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ntrdeal.echoedremnants.block.ModProperties;
import net.ntrdeal.echoedremnants.item.component.EchoedFoodComponent;
import net.ntrdeal.echoedremnants.item.component.ModDataComponents;
import net.ntrdeal.echoedremnants.misc.SculkShriekerAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlock.class)
public abstract class SculkShriekerBlockMixin extends BlockWithEntity implements Waterloggable {
    @Shadow @Final public static BooleanProperty SHRIEKING;
    @Shadow @Final public static BooleanProperty CAN_SUMMON;
    @Unique private static final IntProperty ECHO_SHARDS = ModProperties.ECHO_SHARDS;

    protected SculkShriekerBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "appendProperties", at = @At("TAIL"))
    private void ntrdeal$addShards(StateManager.Builder<Block, BlockState> builder, CallbackInfo ci) {
        builder.add(ECHO_SHARDS);
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (stack.isOf(Items.ECHO_SHARD)) {
            if (state.get(ECHO_SHARDS) < 4) {
                stack.decrementUnlessCreative(1, player);
                world.setBlockState(pos, state.with(ECHO_SHARDS, state.get(ECHO_SHARDS)+1));
                return ItemActionResult.SUCCESS;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @WrapMethod(method = "onSteppedOn")
    private void ntrdeal$echoedCrafting(World world, BlockPos pos, BlockState state, Entity entity, Operation<Void> original) {
        if (state.get(CAN_SUMMON) && !state.get(SHRIEKING) && state.get(ECHO_SHARDS) >= 1 && entity instanceof ItemEntity item
        && item.getStack() instanceof ItemStack stack && (stack.contains(DataComponentTypes.FOOD) || stack.contains(ModDataComponents.ECHOED_FOOD))
        && EchoedFoodComponent.levelUp(stack.get(ModDataComponents.ECHOED_FOOD)) instanceof EchoedFoodComponent component && component.shrieks() < 5) {

            ItemStack newStack = stack.split(1);
            newStack.set(ModDataComponents.ECHOED_FOOD, component);
            ItemScatterer.spawn(world, item.getX(), item.getY(), item.getZ(), newStack);

            if (world instanceof ServerWorld serverWorld && serverWorld.getBlockEntity(pos) instanceof SculkShriekerBlockEntity shrieker) ((SculkShriekerAccessor) shrieker).ntrdeal$shriek(serverWorld, item);

            super.onSteppedOn(world, pos, state, entity);
        } else original.call(world, pos, state, entity);
    }
}
