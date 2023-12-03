package com.chivasss.pocket_dimestions.item.custom;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

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
//        Pair pair = RayTrace.getEntityLookingAt(, range, 1.0f);
//        if (!pLevel.isClientSide()) {
//            //pair = RayTrace.getEntityLookingAt(pPlayer, range, 1.0f);
//            ArrayList<EntityHitResult> entityHitResultList = (ArrayList<EntityHitResult>) pair.getA();
//            for (EntityHitResult entityHitResult : entityHitResultList) {
//                Entity entity = entityHitResult.getEntity();
//                if (entity != null) {
//                    entity.setTicksFrozen(100);
//                    entity.hurt(pPlayer.damageSources().magic(), damage);
//                }
//            }
//        }
//        ParticlePresets.clientSidedParticles((HitResult) pair.getB(), pLevel, 12, -0.5F, 0.5F);

        return super.use(pLevel, pPlayer, pUsedHand);


    }
}

