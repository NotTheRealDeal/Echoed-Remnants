package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.ntrdeal.echoedremnants.block.ShriekerTriggerer;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class SculkShriekerBlockEntityMixin extends BlockEntity implements ShriekerTriggerer {
    @Shadow private int warningLevel;
    @Shadow protected abstract void shriek(ServerLevel level, @Nullable Entity sourceEntity);
    @Shadow protected abstract boolean tryToWarn(ServerLevel level, ServerPlayer player);
    @Shadow protected abstract boolean canRespond(ServerLevel level);

    public SculkShriekerBlockEntityMixin(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    @Override
    public void ntrdeal$tryShriek(ServerLevel level, ServerPlayer player) {
        this.warningLevel = 0;
        if (this.canRespond(level)) this.tryToWarn(level, player);
        this.shriek(level, player);
    }
}
