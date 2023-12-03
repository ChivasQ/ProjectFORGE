package com.chivasss.pocket_dimestions.entity.custom;

import com.mojang.logging.LogUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.PushReaction;
import org.slf4j.Logger;

import java.util.List;

public class PortalEntity extends Entity {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final EntityDataAccessor<Integer> DATA_RADIUS = SynchedEntityData.defineId(PortalEntity.class, EntityDataSerializers.INT);

    int frame = 1;

    public PortalEntity(EntityType<? extends PortalEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
    }

    protected void defineSynchedData() {
        this.getEntityData().define(DATA_RADIUS, frame);
    }

    public void entityInside(Level level, Entity entity) {
        if (level instanceof ServerLevel && !entity.isPassenger() && !entity.isVehicle() && entity.canChangeDimensions() && this.closerThan(entity, 1)) {
            ResourceKey<Level> resourcekey = level.dimension() == Level.END ? Level.OVERWORLD : Level.END;
            ServerLevel serverlevel = ((ServerLevel) level).getServer().getLevel(resourcekey);
            if (serverlevel == null) {
                return;

            }

            entity.changeDimension(serverlevel);
        }

    }

    public void tick() {
        super.tick();
        List<Entity> list1 = this.level().getEntitiesOfClass(Entity.class, this.getBoundingBox());
        if (!list1.isEmpty()) {
            for (Entity entity : list1) {
                if (entity != this) {
                    entityInside(entity.level(), entity);

                }
            }
        }
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.tickCount = tag.getInt("Age");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putInt("Age", this.tickCount);
    }

    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }

    public PushReaction getPistonPushReaction() {
        return PushReaction.IGNORE;
    }

    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    public EntityDimensions getDimensions(Pose p_19721_) {
        return EntityDimensions.fixed(1F, 4.0F);
    }

}