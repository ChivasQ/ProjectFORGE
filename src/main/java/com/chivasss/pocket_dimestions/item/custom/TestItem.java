package com.chivasss.pocket_dimestions.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TestItem extends Item {
    private final int range;
    private final int damage;

    public TestItem(Properties properties) {
        super(properties);
        range = 15;
        damage = 2;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        Vec3 pVec = pPlayer.getEyePosition(.5f);
        Vec3 tVec = pVec.add(pPlayer.getLookAngle().scale(range));
        AABB minMax = new AABB(pVec, tVec);
        List<Entity> list = pLevel.getEntities(pPlayer, minMax);

        for (Entity entity : list) {
            entity.setTicksFrozen(100);
            entity.hurt(pPlayer.damageSources().magic(), damage);
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}

