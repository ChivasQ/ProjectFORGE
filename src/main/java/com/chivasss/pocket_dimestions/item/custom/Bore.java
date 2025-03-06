package com.chivasss.pocket_dimestions.item.custom;


import com.chivasss.pocket_dimestions.entity.client.BoreItemRenderer;
import com.chivasss.pocket_dimestions.sound.ModSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Bore extends Item{
    private SoundInstance loopingSound;
    private SoundInstance cooldownSound;
    private double distance = 0.0;
    private final double range = 128;
    private static final int OVERHEAT_TIME = 200;
    private int COOLDOWN = 100;
    private int COOLDOWN_BEFORE_COOLING = 20;
    private int c = 0;
    private int n = 0;
    private boolean isOverheated = false;
    private int cooldownTimer = 0;
    private boolean isInUse = false;
    private static final ConcurrentHashMap<UUID, BlockBreakingProgress> breakingProgress = new ConcurrentHashMap <>();
    private static final int BREAK_TIME = 2; // 1 секунда (20 тиков)

    public Bore(Properties pProperties) {
        super(pProperties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private BlockEntityWithoutLevelRenderer renderer = null;

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                if (renderer == null) {
                    renderer = new BoreItemRenderer();
                }

                return this.renderer;
            }
            private static final HumanoidModel.ArmPose EXAMPLE_POSE = HumanoidModel.ArmPose.create("EXAMPLE", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.RIGHT) {
                    model.body.xRot = 112.5F;
                    model.body.yRot = 112.5F;
                    model.head.yRot = 112.5F;

                    model.rightArm.xRot = (float) (112.5);
                    model.rightArm.yRot = (float) (112.5);
                    model.leftArm.xRot = (float) (67.5);
                    model.leftArm.yRot = (float) (-112.5);
                }
            });

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return EXAMPLE_POSE;
                    }
                }
                return HumanoidModel.ArmPose.EMPTY;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                Bore item = (Bore) itemInHand.getItem();
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                if (item.isInUse()) {
                    poseStack.translate(i * 0.7F,  (Math.sin(partialTick / 50) + 1) * - 1F,  -1.2F);
                }
                else {
                    //poseStack.scale(2, 2, 2);
                    poseStack.translate(i * 0.7F, - 1F, - 1.2F);
                }
//                if (player.getUseItem() == itemInHand && player.isUsingItem()) {
//                    poseStack.translate(0.56F, -0.5F, -0.72F);
//                    //poseStack.scale(2, 2, 2);
//                }
                return true;
            }

        });
    }

    public int getCooldownTimer() {
        return (COOLDOWN - cooldownTimer);
    }
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.startUsingItem(hand);
        return ItemUtils.startUsingInstantly(world, entity, hand);

    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack pStack, int pRemainingUseDuration) {
        Player player = (Player) entity;
        Vec3 d =  player.getLookAngle();
        //world.addParticle(ParticleTypes.CLOUD, true, player.getX() + d.x,player.getEyeY() + d.y, player.getZ() + d.z, 0,0,0);
        if (isOverheated) {
            return;
        }
        else stopCooldownSound();
        if (!isLoopingSoundPlaying() && entity != null && !world.isClientSide) {
            playLoopingSound(player);
        }
        if (n < OVERHEAT_TIME) {

            Vec3 look = entity.getLookAngle();
            Vec3 start = entity.getEyePosition(1F);
            this.setInUse(true);
            Vec3 end = new Vec3(entity.getX() + look.x * range, entity.getEyeY() + look.y * range, entity.getZ() + look.z * range);
            ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);

            BlockHitResult rayTraceResult = world.clip(context);

            double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);
            this.setDistance(traceDistance);
            AABB playerBox = entity.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);
            for (Entity possible : world.getEntities(entity, playerBox)) {
                AABB entityBox = possible.getBoundingBox().inflate(0.3D); //.deflate(0.3D) or .inflate(0.3D) to scale hitbox
                Optional<Vec3> optional = entityBox.clip(start, rayTraceResult.getLocation());
                if (optional.isPresent()) {
                    Vec3 position = optional.get();
                    double distance = start.distanceToSqr(position);

                    if (distance < traceDistance) {
                        possible.setSecondsOnFire(20);
                        possible.hurt(entity.damageSources().onFire(), 5);
                        possible.hurt(entity.damageSources().mobAttack(entity), (float) entity.getAttributeValue(Attributes.ATTACK_DAMAGE));

                    }
                }
            }
            if (traceDistance <= range) {
                BlockPos targetPos = rayTraceResult.getBlockPos();
                BlockState blockState = world.getBlockState(targetPos);

                if (! blockState.isAir() && blockState.getDestroySpeed(world, targetPos) >= 0) {
                    Bore.BlockBreakingProgress progress = breakingProgress.computeIfAbsent(player.getUUID(),
                            uuid -> new Bore.BlockBreakingProgress(targetPos, 0));


                    progress.progress++;
                    breakBlockInArea(world, player, targetPos, (int) ((progress.progress / (float) BREAK_TIME) * 10));

                    //player.displayClientMessage(Component.literal("Прогресс: " + (int) ((progress.progress / (float) BREAK_TIME) * 100) + "%"), true);

                    if (! world.isClientSide() && progress.progress >= BREAK_TIME) {
                        world.destroyBlock(targetPos, true, player);
                        breakingProgress.remove(player.getUUID());
                    }
                }
            }
            c = 0;
            n++;

        } else {
            setInUse(false);
            setDistance(0);
            n = 0;
            isOverheated = true;
            //player.displayClientMessage(Component.literal("!!! Перегрев !!!"), true);
            stopLoopingSound();
            playCooldownSound(player);
        }
        //player.displayClientMessage(Component.literal(String.valueOf(n)), true);
    }
    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        Player player = (Player) pEntity;

        if (isOverheated) {
            cooldownTimer++;

            //player.displayClientMessage(Component.literal("Перегрев: ждите " + (COOLDOWN - cooldownTimer) + " тиков"), true);

            if (cooldownTimer >= COOLDOWN) {
                isOverheated = false;
                cooldownTimer = 0;
                //player.displayClientMessage(Component.literal("Оружие остыло!"), true);
            }
        } else if (!isInUse && n > 0) {
            if (c >= COOLDOWN_BEFORE_COOLING) {
                n = Math.max(0, n - 1);
                //player.displayClientMessage(Component.literal("Охлаждение... " + n), true);
            } else {
                c++;
            }
            if (n == 0) {
                c = 0;
            }
        }
    }



    private void breakBlockInArea(Level world, Player player, BlockPos blockPos, int progress) {
        world.destroyBlockProgress(player.getId(), blockPos, progress);
    }
    @OnlyIn(Dist.CLIENT)
    private boolean isLoopingSoundPlaying() {
        //System.out.println(loopingSound != null && Minecraft.getInstance().getSoundManager().isActive(loopingSound));
        return loopingSound != null && Minecraft.getInstance().getSoundManager().isActive(loopingSound);
    }
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        this.setDistance(0);
        this.setInUse(false);
        stopLoopingSound();
        return itemStack;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        this.setDistance(0);
        this.setInUse(false);
        stopLoopingSound();
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity livingEntity) {
        this.setDistance(0);
        this.setInUse(false);
        stopLoopingSound();
        //livingEntity.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }
    @OnlyIn(Dist.CLIENT)
    private void playLoopingSound(Player player) {
        if (loopingSound == null) {
            loopingSound = new SimpleSoundInstance(
                    ModSounds.LASERBORE_LOOP.getId(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F,
                    player.getRandom(),
                    true,
                    0,
                    SoundInstance.Attenuation.LINEAR,
                    player.blockPosition().getX(),
                    player.blockPosition().getY(),
                    player.blockPosition().getZ(),
                    false
            );
            if (this.isInUse() && !isOverheated) {
                Minecraft.getInstance().getSoundManager().play(loopingSound);
            }
        }
    }
    @OnlyIn(Dist.CLIENT)
    private void playCooldownSound(Player player) {
        if (cooldownSound == null) {
            cooldownSound = new SimpleSoundInstance(
                    ModSounds.LASERBORE_OVERHEAT.getId(),
                    SoundSource.PLAYERS,
                    1.0F,
                    1.0F,
                    player.getRandom(),
                    false,
                    0,
                    SoundInstance.Attenuation.LINEAR,
                    player.blockPosition().getX(),
                    player.blockPosition().getY(),
                    player.blockPosition().getZ(),
                    false
            );
            Minecraft.getInstance().getSoundManager().play(cooldownSound);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void stopLoopingSound() {
        if (loopingSound != null) {
            Minecraft.getInstance().getSoundManager().stop(loopingSound);
            loopingSound = null;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void stopCooldownSound() {
        if (cooldownSound != null) {
            Minecraft.getInstance().getSoundManager().stop(cooldownSound);
            cooldownSound = null;
        }
    }

    public int getUseDuration(ItemStack p_42933_) {
        return 72000;
    }
    public UseAnim getUseAnimation(ItemStack p_40935_) {
        return UseAnim.BOW;
    }
    public boolean isInUse() {
        return isInUse;
    }
    public void setInUse(boolean inUse) {
        isInUse = inUse;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public boolean isOverheated() {
        return isOverheated;
    }

    public int getN() {
        return n;
    }

    private static class BlockBreakingProgress {
        BlockPos pos;
        int progress;

        BlockBreakingProgress(BlockPos pos, int progress) {
            this.pos = pos;
            this.progress = progress;
        }
    }

}
