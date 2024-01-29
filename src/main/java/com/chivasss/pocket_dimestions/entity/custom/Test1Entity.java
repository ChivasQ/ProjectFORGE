package com.chivasss.pocket_dimestions.entity.custom;

import com.chivasss.pocket_dimestions.entity.ModEntityTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.PartEntity;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Test1Entity extends Entity implements TraceableEntity {
    @Nullable
    public static LivingEntity owner;
    @Nullable
    private UUID ownerUUID;
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double lerpXRot;
    public Test1Entity(EntityType<? extends Test1Entity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.noPhysics = true;
    }
    public Test1Entity(Level pLevel, double pX, double pY, double pZ){
        super(ModEntityTypes.TEST.get(), pLevel);
        this.setPos(pX, pY, pZ);
    }



    @Override
    public void tick() {
        if (!this.level().isClientSide && owner != null){
            Vec3 vec3 = getOwner().getPosition(1).add(0,2,0);
            //this.move(MoverType.SELF, getOwner().getPosition(1).add(0,2,0));
            //this.lerpTo(vec3.x,vec3.y,vec3.z, 0,0,0,false);
            //this.moveTo(getOwner().getPosition(1).add(0,2,0));
            //this.lookAt(EntityAnchorArgument.Anchor.FEET, getOwner().getPosition(1));
            //this.teleportTo(vec3.x,vec3.y,vec3.z);
        }
        this.tickLerp();
        super.tick();
    }
    public void lerpTo(double pX, double pY, double pZ, float pYaw, float pPitch, int pPosRotationIncrements, boolean pTeleport) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.lerpYRot = (double)pYaw;
        this.lerpXRot = (double)pPitch;
        this.lerpSteps = 5;
    }
    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpSteps > 0) {
            double d0 = this.getX() + (this.lerpX - this.getX()) / (double)this.lerpSteps;
            double d1 = this.getY() + (this.lerpY - this.getY()) / (double)this.lerpSteps;
            double d2 = this.getZ() + (this.lerpZ - this.getZ()) / (double)this.lerpSteps;
            double d3 = Mth.wrapDegrees(this.lerpYRot - (double)this.getYRot());
            this.setYRot(this.getYRot() + (float)d3 / (float)this.lerpSteps);
            this.setXRot(this.getXRot() + (float)(this.lerpXRot - (double)this.getXRot()) / (float)this.lerpSteps);
            --this.lerpSteps;
            this.setPos(d0, d1, d2);
            this.setRot(this.getYRot(), this.getXRot());
        }
    }
    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.hasUUID("Owner")) {
            this.ownerUUID = pCompound.getUUID("Owner");
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        if (this.ownerUUID != null) {
            pCompound.putUUID("Owner", this.ownerUUID);
        }
    }
    public void setOwner(@javax.annotation.Nullable LivingEntity pOwner) {
        this.owner = pOwner;
        this.ownerUUID = pOwner == null ? null : pOwner.getUUID();
    }

    @Nullable

    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level() instanceof ServerLevel) {
            Entity entity = ((ServerLevel)this.level()).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity) {
                this.owner = (LivingEntity)entity;
            }
        }

        return this.owner;
    }
    @Override
    public @Nullable PartEntity<?>[] getParts() {
        return super.getParts();
    }

    public EntityDimensions getDimensions(Pose pPose) {
        return EntityDimensions.fixed(0.5F, 0.5F);
    }

}
