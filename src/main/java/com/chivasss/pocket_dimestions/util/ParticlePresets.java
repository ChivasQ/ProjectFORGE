package com.chivasss.pocket_dimestions.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ParticlePresets {



    public static void clientParticles(BlockHitResult hitResult, Level level, Player player) {
        RandomSource randomsource = RandomSource.create();
        double d0 = hitResult.getBlockPos().getX();
        double d1 = hitResult.getBlockPos().getY();
        double d2 = hitResult.getBlockPos().getZ();
        BlockState blockstate = level.getBlockState(hitResult.getBlockPos());
        player.sendSystemMessage(Component.literal(String.valueOf(blockstate)));
        if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            switch (hitResult.getDirection()) {
                case UP:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX()+0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d1 = hitResult.getBlockPos().getY()+1;
                        d2 = hitResult.getBlockPos().getZ()+0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 1.0D, 0.0D);
                    }
                    break;
                }

                case DOWN:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d1 = hitResult.getBlockPos().getY();
                        d2 = hitResult.getBlockPos().getZ() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, -1.0D, 0.0D);
                    }
                    break;
                }

                case EAST:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX() + 1;
                        d1 = hitResult.getBlockPos().getY() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d2 = hitResult.getBlockPos().getZ() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 1.0D, 0D, 0.0D);
                    }
                    break;
                }
                case WEST:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX();
                        d1 = hitResult.getBlockPos().getY() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d2 = hitResult.getBlockPos().getZ() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, -1.0D, 0D, 0.0D);
                    }
                    break;
                }
                case NORTH:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);;
                        d1 = hitResult.getBlockPos().getY() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d2 = hitResult.getBlockPos().getZ();
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0D, 0D, -1.0D);
                    }
                    break;
                }
                case SOUTH:
                {
                    for(int i = 0; i < 12; ++i) {
                        d0 = hitResult.getBlockPos().getX() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);;
                        d1 = hitResult.getBlockPos().getY() + 0.5 + (double) Mth.randomBetween(randomsource, -1F, 1F);
                        d2 = hitResult.getBlockPos().getZ() + 1;
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0D, 0D, 1.0D);
                    }
                    break;
                }
            }
//            for(int i = 0; i < 12; ++i) {
//
//                level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 1.0D, 0.0D);
//
//            }
        }
    }
}
