package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.util.Particless;
import com.chivasss.pocket_dimestions.util.RayTrace;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        if (!pLevel.isClientSide()) {
            ArrayList<EntityHitResult> entityHitResultList = RayTrace.getEntityLookingAt(pPlayer, range, 1.0f, true);
            for (EntityHitResult entityHitResult : entityHitResultList){
                    Entity entity = entityHitResult.getEntity();
                if (entity != null) {
                    entity.setTicksFrozen(100);

                    entity.hurt(pPlayer.damageSources().magic(), damage);
                }
            }

        }
            return super.use(pLevel, pPlayer, pUsedHand);


    }
}

