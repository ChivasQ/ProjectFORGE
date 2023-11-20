package com.chivasss.pocket_dimestions.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RandomEffects extends Item {
    public RandomEffects(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide() && hand == InteractionHand.MAIN_HAND) {

            //random
            RandomEffect(player);
            //Cooldown
            player.getCooldowns().addCooldown(this, 100);

        }

        return super.use(level, player, hand);
    }

    private void RandomEffect(Player player) {


        for (int i = 0; i < 5; i++) {
            int number = getRandomNumber();
            player.sendSystemMessage(Component.literal(Integer.toString(number)));
            if (number == 0) {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 1) {
                player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 2) {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 3) {
                player.addEffect(new MobEffectInstance(MobEffects.JUMP, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 4) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 5) {
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 6) {
                player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 7) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 8) {
                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 9) {
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 10) {
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 11) {
                player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 12) {
                player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 13) {
                player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
            if (number == 14) {
                player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 40, RandomSource.createNewThreadLocalInstance().nextInt(10)));
            }
        }
    }

    private int getRandomNumber() {
        return RandomSource.createNewThreadLocalInstance().nextInt(15);
    }
}
