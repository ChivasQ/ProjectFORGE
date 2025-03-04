package com.chivasss.pocket_dimestions.util;


import com.chivasss.pocket_dimestions.entity.custom.rune_turret.RuneTurretEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Predicate;

public class RayTrace {
    private RayTrace() {
        // nothing to do
    }



    public static void getEntityLookingAt(RuneTurretEntity player, double range, float ticks) {
        Level level = player.level();

        Vec3 look = player.getLookAngle();
        Vec3 start = player.getEyePosition(ticks);

        Vec3 end = new Vec3(player.getX() + look.x * range, player.getEyeY() + look.y * range, player.getZ() + look.z * range);
        ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player);

        HitResult rayTraceResult = level.clip(context);

        double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);

        AABB playerBox = player.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);

        Predicate<Entity> filter = entity -> !entity.isSpectator() && entity.isPickable() && entity instanceof LivingEntity;
        ArrayList<EntityHitResult> arrayList = new ArrayList<EntityHitResult>();

        for (Entity possible : level.getEntities(player, playerBox, filter)) {
            AABB entityBox = possible.getBoundingBox(); //.deflate(0.3D) or .inflate(0.3D) to scale hitbox
            Optional<Vec3> optional = entityBox.clip(start, end);
            if (optional.isPresent()) {
                Vec3 position = optional.get();
                double distance = start.distanceToSqr(position);

                if (distance < traceDistance) {


                }
            }
        }
        //return new Pair<ArrayList<EntityHitResult>, HitResult>(arrayList, rayTraceResult);
    }

    @CheckForNull
    public static EntityHitResult traceToEntity(Player player, Entity target)
    {
        return traceToEntity(player, target, 1.0F);
    }

    @CheckForNull
    public static EntityHitResult traceToEntity(Player player, Entity target, float ticks) {
        Vec3 start = player.getEyePosition(ticks);
        Vec3 end = target.position();

        AABB targetBox = target.getBoundingBox().inflate(0.3D);
        Optional<Vec3> optional = targetBox.clip(start, end);

        return optional.map(vector3d -> new EntityHitResult(target, vector3d)).orElse(null);
    }



}