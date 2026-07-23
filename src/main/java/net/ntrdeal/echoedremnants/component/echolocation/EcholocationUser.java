package net.ntrdeal.echoedremnants.component.echolocation;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationInfo;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;

public interface EcholocationUser extends VibrationSystem.User {
    Echolocation echolocation();

    @Override
    default boolean canReceiveVibration(ServerLevel level, BlockPos pos, Holder<GameEvent> event, GameEvent.Context context) {
        return this.echolocation().isActive();
    }

    @Override
    default int getListenerRadius() {
        return this.echolocation().range();
    }

    interface Ticker {
        static void tick(final Level level, final VibrationSystem.Data data, final VibrationSystem.User user) {
            if (level instanceof ServerLevel serverLevel) {
                if (data.getCurrentVibration() == null) {
                    trySelectAndScheduleVibration(serverLevel, data, user);
                }

                if (data.getCurrentVibration() != null) {
                    boolean hasChanged = data.getTravelTimeInTicks() > 0;
                    data.decrementTravelTime();
                    if (data.getTravelTimeInTicks() <= 0) {
                        hasChanged = receiveVibration(serverLevel, data, user, data.getCurrentVibration());
                    }

                    if (hasChanged) {
                        user.onDataChanged();
                    }
                }
            }
        }

        private static void trySelectAndScheduleVibration(final ServerLevel serverLevel, final VibrationSystem.Data data, final VibrationSystem.User user) {
            data.getSelectionStrategy()
                    .chosenCandidate(serverLevel.getGameTime())
                    .ifPresent(
                            context -> {
                                data.setCurrentVibration(context);
                                data.setTravelTimeInTicks(user.calculateTravelTimeInTicks(context.distance()));
                                user.onDataChanged();
                                data.getSelectionStrategy().startOver();
                            }
                    );
        }

        private static boolean receiveVibration(
                final ServerLevel serverLevel, final VibrationSystem.Data data, final VibrationSystem.User user, final VibrationInfo currentVibration
        ) {
            BlockPos origin = BlockPos.containing(currentVibration.pos());
            BlockPos destination = user.getPositionSource().getPosition(serverLevel).map(BlockPos::containing).orElse(origin);
            if (user.requiresAdjacentChunksToBeTicking() && !areAdjacentChunksTicking(serverLevel, destination)) {
                return false;
            }

            user.onReceiveVibration(
                    serverLevel,
                    origin,
                    currentVibration.gameEvent(),
                    currentVibration.getEntity(serverLevel).orElse(null),
                    currentVibration.getProjectileOwner(serverLevel).orElse(null),
                    VibrationSystem.Listener.distanceBetweenInBlocks(origin, destination)
            );
            data.setCurrentVibration(null);
            return true;
        }

        private static boolean areAdjacentChunksTicking(final Level level, final BlockPos listenerPos) {
            ChunkPos listenerChunkPos = ChunkPos.containing(listenerPos);

            for (int x = listenerChunkPos.x() - 1; x <= listenerChunkPos.x() + 1; x++) {
                for (int z = listenerChunkPos.z() - 1; z <= listenerChunkPos.z() + 1; z++) {
                    if (!level.shouldTickBlocksAt(ChunkPos.pack(x, z)) || level.getChunkSource().getChunkNow(x, z) == null) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
