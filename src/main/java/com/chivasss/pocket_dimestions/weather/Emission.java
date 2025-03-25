package com.chivasss.pocket_dimestions.weather;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class Emission extends Weather{

    public Emission(int duration) {
        super(duration, WeatherType.EMISSION);
    }

    @Override
    public void tick(Level level) {
        for (Player player : level.players()) {
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));

            level.addParticle(ParticleTypes.END_ROD, player.getX(), player.getY() + 1.5, player.getZ(),
                    (level.getRandom().nextFloat() - 0.5) * 0.2,
                    (level.getRandom().nextFloat() - 0.5) * 0.2,
                    (level.getRandom().nextFloat() - 0.5) * 0.2);

            if (level.getRandom().nextFloat() < 0.05) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, false, false));
            }
        }
    }
}
