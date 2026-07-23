package net.ntrdeal.echoedremnants.block;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public interface ShriekerTriggerer {
    void ntrdeal$tryShriek(ServerLevel level, ServerPlayer player);
}
