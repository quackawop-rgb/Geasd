package com.geasd.geasd.harvest;

import com.geasd.geasd.Config;
import com.geasd.geasd.block.ModBlocks;
import com.geasd.geasd.block.PlantKind;
import com.geasd.geasd.block.PlantStubbleBlock;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public final class PlantHarvestHelper {
    private PlantHarvestHelper() {
    }

    public static boolean isHarvestable(BlockState state) {
        Block block = state.getBlock();
        return block == Blocks.SHORT_GRASS
                || block == Blocks.TALL_GRASS
                || block == Blocks.FERN
                || block == Blocks.LARGE_FERN;
    }

    public static boolean isStubble(BlockState state) {
        return state.is(ModBlocks.PLANT_STUBBLE);
    }

    public static BlockPos getRootPos(Level level, BlockPos pos, BlockState state) {
        if (state.getBlock() instanceof DoublePlantBlock && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
            return pos.below();
        }
        return pos;
    }

    public static PlantKind getPlantKind(BlockState state) {
        Block block = state.getBlock();
        if (block == Blocks.SHORT_GRASS) {
            return PlantKind.SHORT_GRASS;
        }
        if (block == Blocks.TALL_GRASS) {
            return PlantKind.TALL_GRASS;
        }
        if (block == Blocks.FERN) {
            return PlantKind.FERN;
        }
        if (block == Blocks.LARGE_FERN) {
            return PlantKind.LARGE_FERN;
        }
        throw new IllegalArgumentException("Unsupported harvestable block: " + block);
    }

    public static void breakPlant(Level level, BlockPos pos, BlockState state, Player player, boolean shears) {
        breakBlocks(level, pos, state, player, shears);
    }

    public static void breakStubble(Level level, BlockPos pos, Player player, boolean shears) {
        BlockState state = level.getBlockState(pos);
        playBreakEffects(level, pos, state, shears);
        level.removeBlock(pos, false);
    }

    public static void convertToStubble(Level level, BlockPos pos, BlockState state, Player player) {
        BlockPos rootPos = getRootPos(level, pos, state);
        BlockState rootState = level.getBlockState(rootPos);
        if (!isHarvestable(rootState)) {
            return;
        }

        PlantKind kind = getPlantKind(rootState);
        breakBlocks(level, pos, state, player, true);

        BlockState stubbleState = ModBlocks.PLANT_STUBBLE.defaultBlockState()
                .setValue(PlantStubbleBlock.KIND, kind);
        level.setBlock(rootPos, stubbleState, Block.UPDATE_ALL);
        level.scheduleTick(rootPos, ModBlocks.PLANT_STUBBLE, Config.REGROWTH_TICKS);
    }

    public static void restorePlant(Level level, BlockPos pos, PlantKind kind) {
        switch (kind) {
            case SHORT_GRASS -> level.setBlock(pos, Blocks.SHORT_GRASS.defaultBlockState(), Block.UPDATE_ALL);
            case FERN -> level.setBlock(pos, Blocks.FERN.defaultBlockState(), Block.UPDATE_ALL);
            case TALL_GRASS -> DoublePlantBlock.placeAt(level, Blocks.TALL_GRASS.defaultBlockState(), pos, Block.UPDATE_ALL);
            case LARGE_FERN -> DoublePlantBlock.placeAt(level, Blocks.LARGE_FERN.defaultBlockState(), pos, Block.UPDATE_ALL);
        }
    }

    private static void breakBlocks(Level level, BlockPos pos, BlockState state, Player player, boolean shears) {
        BlockPos rootPos = getRootPos(level, pos, state);
        BlockState rootState = level.getBlockState(rootPos);

        if (rootState.getBlock() instanceof DoublePlantBlock) {
            BlockPos upperPos = rootPos.above();
            BlockState upperState = level.getBlockState(upperPos);
            if (upperState.getBlock() instanceof DoublePlantBlock) {
                playBreakEffects(level, upperPos, upperState, shears);
                level.removeBlock(upperPos, false);
            }
        }

        playBreakEffects(level, rootPos, rootState, shears);
        level.removeBlock(rootPos, false);
    }

    private static void playBreakEffects(Level level, BlockPos pos, BlockState state, boolean shears) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        serverLevel.levelEvent(net.minecraft.world.level.block.LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        serverLevel.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);

        if (shears) {
            serverLevel.playSound(null, pos, SoundEvents.SHEARS_SNIP, SoundSource.PLAYERS, 1.0F, 1.0F);
        }
    }
}
