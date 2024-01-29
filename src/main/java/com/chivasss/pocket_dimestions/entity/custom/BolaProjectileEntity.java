package com.chivasss.pocket_dimestions.entity.custom;

import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public class BolaProjectileEntity extends ThrowableItemProjectile {

    public BolaProjectileEntity(Level pLevel, LivingEntity pShooter) {
        super(ModEntityTypes.BOLA.get(), pShooter, pLevel);
        this.noPhysics = false;

    }
    public BolaProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BolaProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public BolaProjectileEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.ZINC.get();
    }

    @Override
    protected void onHit(HitResult pResult) {

        LocalPlayer player =  Minecraft.getInstance().player;
        if (player == null) return;
        player.sendSystemMessage(Component.literal("111"));
        super.onHit(pResult);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canCollideWith(Entity pEntity) {
        return true;
    }
}
