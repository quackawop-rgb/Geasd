package com.geasd.geasd.block;

import com.geasd.geasd.Geasd;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static PlantStubbleBlock PLANT_STUBBLE;

    public static void register() {
        PLANT_STUBBLE = Registry.register(
                BuiltInRegistries.BLOCK,
                Geasd.id("plant_stubble"),
                new PlantStubbleBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.SHORT_GRASS)
                                .setId(Geasd.blockKey("plant_stubble"))
                                .noCollision()
                                .instabreak()
                                .sound(SoundType.GRASS)
                                .noOcclusion()
                                .replaceable()));

        Registry.register(
                BuiltInRegistries.ITEM,
                Geasd.id("plant_stubble"),
                new BlockItem(PLANT_STUBBLE, new Item.Properties().setId(Geasd.itemKey("plant_stubble"))));
    }
}
