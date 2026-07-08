package com.geasd.geasd;

import com.geasd.geasd.block.ModBlocks;
import com.geasd.geasd.harvest.GrassSeedHarvestHandler;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Geasd implements ModInitializer {
    public static final String MOD_ID = "geasd";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModBlocks.register();
        GrassSeedHarvestHandler.register();
        LOGGER.info("Geasd initialized (Fabric)");
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    public static ResourceKey<net.minecraft.world.level.block.Block> blockKey(String path) {
        return ResourceKey.create(Registries.BLOCK, id(path));
    }

    public static ResourceKey<net.minecraft.world.item.Item> itemKey(String path) {
        return ResourceKey.create(Registries.ITEM, id(path));
    }
}
