package net.ntrdeal.echoedremnants.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.ntrdeal.echoedremnants.reference.ModBlockItemIds;

public class ModBlocks {
    public static final IntegerProperty SHARDS_PROPERTY = IntegerProperty.create("shards", 0, 4);

    public static final Block ROSE_GOLD_BLOCK = Blocks.register(ModBlockItemIds.ROSE_GOLD_BLOCK.block(), BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK));

    public static void register() {
    }
}
