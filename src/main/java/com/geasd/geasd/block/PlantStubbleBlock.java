package com.geasd.geasd.block;

import com.geasd.geasd.harvest.PlantHarvestHelper;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class PlantStubbleBlock extends BushBlock {
    public static final EnumProperty<PlantKind> KIND = EnumProperty.create("kind", PlantKind.class);

    public PlantStubbleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(KIND, PlantKind.SHORT_GRASS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(KIND);
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        PlantHarvestHelper.restorePlant(level, pos, state.getValue(KIND));
    }
}
