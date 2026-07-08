package com.geasd.geasd;

import com.geasd.geasd.block.ModBlocks;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockColorRegistry;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ARGB;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class GeasdClient implements ClientModInitializer {
    private static final int DEFAULT_GRASS_COLOR = ARGB.opaque(0x91BD59);

    @Override
    public void onInitializeClient() {
        BlockColorRegistry.register(List.of(new BlockTintSource() {
            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                if (level == null || pos == null) {
                    return DEFAULT_GRASS_COLOR;
                }
                return BiomeColors.getAverageGrassColor(level, pos);
            }

            @Override
            public int color(BlockState state) {
                return DEFAULT_GRASS_COLOR;
            }
        }), ModBlocks.PLANT_STUBBLE);
    }
}
