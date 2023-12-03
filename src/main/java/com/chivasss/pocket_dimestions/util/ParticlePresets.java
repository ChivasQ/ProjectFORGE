package com.chivasss.pocket_dimestions.util;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class ParticlePresets {



    public static void clientSidedParticles(HitResult hitResult, Level level, int iter, float min, float max) {
        BlockHitResult blockHitResult = (BlockHitResult) hitResult;
        RandomSource randomsource = RandomSource.create();
        double d0 = blockHitResult.getBlockPos().getX();
        double d1 = blockHitResult.getBlockPos().getY();
        double d2 = blockHitResult.getBlockPos().getZ();
        BlockState blockstate = level.getBlockState(blockHitResult.getBlockPos());
        //player.sendSystemMessage(Component.literal(String.valueOf(blockstate)));
        if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
            switch (blockHitResult.getDirection()) {
                case UP:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x + (double) Mth.randomBetween(randomsource, min, max);
                        d1 = hitResult.getLocation().y;
                        d2 = hitResult.getLocation().z + (double) Mth.randomBetween(randomsource, min, max);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 1.0D, 0.0D);
                    }
                    break;
                }

                case DOWN:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x + (double) Mth.randomBetween(randomsource, min, max);
                        d1 = hitResult.getLocation().y - .1F;
                        d2 = hitResult.getLocation().z + (double) Mth.randomBetween(randomsource, min, max);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, -1.0D, 0.0D);
                    }
                    break;
                }

                case EAST:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x;
                        d1 = hitResult.getLocation().y + (double) Mth.randomBetween(randomsource, min, max);
                        d2 = hitResult.getLocation().z + (double) Mth.randomBetween(randomsource, min, max);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 1.0D, 0D, 0.0D);
                    }
                    break;
                }
                case WEST:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x - .1F;
                        d1 = hitResult.getLocation().y + (double) Mth.randomBetween(randomsource, min, max);
                        d2 = hitResult.getLocation().z + (double) Mth.randomBetween(randomsource, min, max);
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, -1.0D, 0D, 0.0D);
                    }
                    break;
                }
                case NORTH:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x + (double) Mth.randomBetween(randomsource, min, max);;
                        d1 = hitResult.getLocation().y + (double) Mth.randomBetween(randomsource, min, max);
                        d2 = hitResult.getLocation().z - .1F;
                        level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0D, 0D, -1.0D);
                    }
                    break;
                }
                case SOUTH:
                {
                    for(int i = 0; i < iter; ++i) {
                        d0 = hitResult.getLocation().x + (double) Mth.randomBetween(randomsource, min, max);;
                        d1 = hitResult.getLocation().y + (double) Mth.randomBetween(randomsource, min, max);
                        d2 = hitResult.getLocation().z + .1F;
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
