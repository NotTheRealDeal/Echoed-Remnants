package net.ntrdeal.echoedremnants.misc;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.Nullable;

public interface SculkShriekerAccessor {
    void ntrdeal$shriek(ServerWorld serverWorld, @Nullable Entity entity);
}
