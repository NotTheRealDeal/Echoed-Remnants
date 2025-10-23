package net.ntrdeal.echoedremnants.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;
import net.ntrdeal.echoedremnants.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class ModBlocks {

    public static final Block ROSE_GOLD_BLOCK = register("rose_gold_block",
            new Block(AbstractBlock.Settings.copy(Blocks.GOLD_BLOCK)),
            new Item.Settings());

    public static <T extends Block> T register(String name, T block, @Nullable Item.Settings settings){
        if (settings != null) ModItems.register(name, new BlockItem(block, settings));
        return Registry.register(Registries.BLOCK, Identifier.of(EchoedRemnants.MOD_ID, name), block);
    }

    public static void register(){}
}
