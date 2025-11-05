package net.ntrdeal.echoedremnants.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.SculkShriekerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.Vibrations;
import net.minecraft.world.event.listener.GameEventListener;
import net.ntrdeal.echoedremnants.block.ModProperties;
import net.ntrdeal.echoedremnants.misc.SculkShriekerAccessor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SculkShriekerBlockEntity.class)
public abstract class SculkShriekerBlockEntityMixin extends BlockEntity implements GameEventListener.Holder<Vibrations.VibrationListener>, Vibrations, SculkShriekerAccessor {

    @Unique private int shrieks = 0;

    @Shadow protected abstract void shriek(ServerWorld world, @Nullable Entity entity);
    @Shadow private int warningLevel;
    @Shadow protected abstract boolean trySyncWarningLevel(ServerWorld world, ServerPlayerEntity player);

    public SculkShriekerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void ntrdeal$shriek(ServerWorld world, @Nullable Entity entity) {
        if (SculkShriekerBlockEntity.findResponsiblePlayerFromEntity(entity) instanceof ServerPlayerEntity player) {
            this.warningLevel = 0;
            this.trySyncWarningLevel(world, player);
        } this.shriek(world, entity);
    }

    @Inject(method = "shriek(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void ntrdeal$useFuel(ServerWorld world, Entity entity, CallbackInfo ci) {
        int fuel = this.getCachedState().get(ModProperties.ECHO_SHARDS);
        if (fuel >= 1) {
            this.shrieks++;
            if (this.shrieks >= 4) {
                world.setBlockState(this.getPos(), this.getCachedState().with(ModProperties.ECHO_SHARDS, fuel - 1));
                this.shrieks = 0;
            }
        } else this.shrieks = 0;
    }

    @Inject(method = "readNbt", at = @At("TAIL"))
    private void ntrdeal$loadNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        this.shrieks = nbt.getInt("shrieks");
    }

    @Inject(method = "writeNbt", at = @At("TAIL"))
    private void ntrdeal$saveNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfo ci) {
        nbt.putInt("shrieks", this.shrieks);
    }
}
