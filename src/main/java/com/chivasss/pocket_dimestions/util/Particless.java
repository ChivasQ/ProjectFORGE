package com.chivasss.pocket_dimestions.util;

import com.google.common.io.Closer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

public class Particless {
    private RandomSource RandomSource;

    public RandomSource getRandom() {
        return net.minecraft.util.RandomSource.create();
    }
    public void clientParticles(HitResult hitResult, Level level) {

            RandomSource randomsource = this.getRandom();
            BlockState blockstate = level.getBlockState(new BlockPos(
                    (int) hitResult.getLocation().x(),
                    (int) hitResult.getLocation().y(),
                    (int) hitResult.getLocation().z()));
            if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                for(int i = 0; i < 30; ++i) {
                    double d0 = hitResult.getLocation().x() + (double) Mth.randomBetween(randomsource, -1F, 1F);
                    double d1 = hitResult.getLocation().y();
                    double d2 = hitResult.getLocation().z() + (double)Mth.randomBetween(randomsource, -1F, 1F);
                    level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate), d0, d1, d2, 0.0D, 0.0D, 0.0D);

            }
        }

    }
}
