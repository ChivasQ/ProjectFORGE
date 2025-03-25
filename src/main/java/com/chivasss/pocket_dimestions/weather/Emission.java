package com.chivasss.pocket_dimestions.weather;

import com.chivasss.pocket_dimestions.util.PlayerUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
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
            int blocksAbove = PlayerUtils.blocksAbove(player, 3);
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, 0, false, false));

            level.addParticle(ParticleTypes.END_ROD, player.getX(), player.getY() + 3, player.getZ(),
                    0,
                    0,
                    0);

            if (level.getRandom().nextFloat() < 0.05) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, 0, false, false));
            }

            if (duration - getRemainingTime() > 40 && blocksAbove == 0) {
                player.hurt(player.damageSources().magic(), 1);
            }
        }
    }
}
