package net.ntrdeal.echoedremnants.block;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.ntrdeal.echoedremnants.EchoedRemnants;

public class ModBlockTags {

    public static final TagKey<Block> CANNOT_ECHO = register("cannot_echo");

    private static TagKey<Block> register(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(EchoedRemnants.MOD_ID, id));
    }
}