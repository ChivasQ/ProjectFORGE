package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.entity.custom.Test1Entity;
import com.chivasss.pocket_dimestions.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.function.Predicate;

public class MurmillonAmuletItem extends Item {
    int radius = 3;
    public MurmillonAmuletItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);

        AABB aabb = new AABB(
                pPlayer.getX() - radius, pPlayer.getEyeY() - radius, pPlayer.getZ() - radius,
                pPlayer.getX() + radius, pPlayer.getEyeY() + radius, pPlayer.getZ() + radius);

        Predicate<Entity> filter = entity -> !entity.isSpectator() && !entity.isPickable() && entity instanceof Projectile;

        List<Entity> entities = pLevel.getEntities(pPlayer, aabb, filter);
        for (Entity entity : entities){
            System.out.println(entity);
        }
        pPlayer.isBlocking();

        Test1Entity entity = new Test1Entity(pLevel, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ());
        entity.setOwner(pPlayer);
        pLevel.addFreshEntity(entity);
        pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), ModSounds.SOME_SOUND.get(), SoundSource.MASTER, 5f, 0.3f,0);

        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

}
