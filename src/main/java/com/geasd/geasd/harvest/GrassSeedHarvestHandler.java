package com.geasd.geasd.harvest;

import com.geasd.geasd.Config;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class GrassSeedHarvestHandler {
    private GrassSeedHarvestHandler() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register(GrassSeedHarvestHandler::onUseBlock);
        PlayerBlockBreakEvents.AFTER.register(GrassSeedHarvestHandler::onBlockBreak);
    }

    private static InteractionResult onUseBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);

        boolean stubble = PlantHarvestHelper.isStubble(state);
        boolean harvestable = PlantHarvestHelper.isHarvestable(state);

        if (!stubble && !harvestable) {
            return InteractionResult.PASS;
        }

        ItemStack stack = player.getItemInHand(hand);
        boolean emptyHand = stack.isEmpty();
        boolean shears = stack.is(Items.SHEARS);

        if (harvestable && !emptyHand && !shears) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide()) {
            player.swing(hand);
            return InteractionResult.SUCCESS;
        }

        if (stubble) {
            PlantHarvestHelper.breakStubble(level, pos, player, shears);
        } else if (emptyHand) {
            BlockPos dropPos = PlantHarvestHelper.getRootPos(level, pos, state);
            PlantHarvestHelper.breakPlant(level, pos, state, player, false);
            dropSeeds(level, dropPos, rollSeedCount(level.getRandom(), Config.HAND_ONE_SEED_CHANCE));
        } else {
            BlockPos dropPos = PlantHarvestHelper.getRootPos(level, pos, state);
            PlantHarvestHelper.convertToStubble(level, pos, state, player);
            dropSeeds(level, dropPos, rollSeedCount(level.getRandom(), Config.SHEARS_ONE_SEED_CHANCE));
            stack.hurtAndBreak(1, player, hand);
        }

        player.swing(hand, true);
        return InteractionResult.SUCCESS;
    }

    private static void onBlockBreak(Level level, Player player, BlockPos pos, BlockState state, net.minecraft.world.level.block.entity.BlockEntity blockEntity) {
        if (level.isClientSide()) {
            return;
        }

        if (!PlantHarvestHelper.isHarvestable(state)) {
            return;
        }

        if (!isHoldingShears(player)) {
            return;
        }

        level.playSound(null, pos, SoundEvents.SHEARS_SNIP, SoundSource.PLAYERS, 1.0F, 1.0F);
        dropSeeds(level, PlantHarvestHelper.getRootPos(level, pos, state),
                rollSeedCount(level.getRandom(), Config.SHEARS_ONE_SEED_CHANCE));
    }

    private static boolean isHoldingShears(Player player) {
        return player.getMainHandItem().is(Items.SHEARS) || player.getOffhandItem().is(Items.SHEARS);
    }

    static int rollSeedCount(RandomSource random, float oneSeedChance) {
        return random.nextFloat() < oneSeedChance ? 1 : 2;
    }

    private static void dropSeeds(Level level, BlockPos pos, int count) {
        Block.popResource(level, pos, new ItemStack(Items.WHEAT_SEEDS, count));
    }
}
