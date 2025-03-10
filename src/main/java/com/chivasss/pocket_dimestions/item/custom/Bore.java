package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.entity.client.BoreItemRenderer;
import com.chivasss.pocket_dimestions.network.S2CBreakBlockPacket;
import com.chivasss.pocket_dimestions.network.PacketHandler;
import com.chivasss.pocket_dimestions.network.S2CUpdateLaserUsePacket;
import com.chivasss.pocket_dimestions.sound.ModSounds;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class Bore extends Item {
    private SoundInstance loopingSound;
    private SoundInstance cooldownSound;
    private double distance = 0.0;
    private final double range = 128;
    private static final int OVERHEAT_TIME = 200;
    private int COOLDOWN = 80;
    private int COOLDOWN_BEFORE_COOLING = 20;
    private int temperature = 0;
    private boolean isOverheated = false;
    private int cooldownTimer = 0;
    private static final ConcurrentHashMap<UUID, BlockBreakingProgress> breakingProgress = new ConcurrentHashMap <>();
    private static final int BREAK_TIME = 8;
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
            private static final HumanoidModel.ArmPose EXAMPLE_POSE1 = HumanoidModel.ArmPose.create("EXAMPLE1", false, (model, entity, arm) -> {
                if ((arm == HumanoidArm.RIGHT) && (entity instanceof LivingEntity)) {
                    LivingEntity livingEntity = entity;
                    float pitch = livingEntity.getXRot();
                    float headYaw = livingEntity.getYHeadRot();
                    float bodyYaw = livingEntity.yBodyRot;


                    float yawDiff = headYaw - bodyYaw;
                    float pitchRadians = (pitch-90) * Mth.DEG_TO_RAD;
                    float yawRadians = yawDiff * Mth.DEG_TO_RAD;

                    // Применяем углы к правой руке
                    model.rightArm.xRot = pitchRadians;
                    model.rightArm.yRot = yawRadians;

                    model.rightArm.zRot = 0.0F;
                }
            });


            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                if (!itemStack.isEmpty()) {
                    if (entityLiving.getUsedItemHand() == hand && entityLiving.getUseItemRemainingTicks() > 0) {
                        return EXAMPLE_POSE1;
                    }
                }
                return EXAMPLE_POSE1;
            }

            @Override
            public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                Bore item = (Bore) itemInHand.getItem();
                int i = arm == HumanoidArm.RIGHT ? 1 : -1;
                if (player.isUsingItem()) {
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
        Vec3 look = entity.getLookAngle();
        Vec3 start = entity.getEyePosition(1F);
        CompoundTag tag = pStack.getOrCreateTag();
        //Vec3 d =  player.getLookAngle();
        //world.addParticle(ParticleTypes.CLOUD, true, player.getX() + d.x,player.getEyeY() + d.y, player.getZ() + d.z, 0,0,0);
        int temperature = tag.getInt("temperature");
        boolean isOverheated = tag.getBoolean("isOverheated");

        if (isOverheated) return;
        stopCooldownSound();

        if (!isLoopingSoundPlaying() && entity != null && !world.isClientSide) {
            playLoopingSound(player);
        }

        if (temperature < OVERHEAT_TIME) {
            this.setInUse(true, tag);
            Vec3 end = new Vec3(entity.getX() + look.x * range, entity.getEyeY() + look.y * range, entity.getZ() + look.z * range);
            ClipContext context = new ClipContext(start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, entity);
            HitResult rayTraceResult = world.clip(context);

            double traceDistance = rayTraceResult.getLocation().distanceToSqr(start);
            setDistance(traceDistance, tag);

            damageEntities(world, entity, look, traceDistance, start, (BlockHitResult) rayTraceResult);

            breakBlockByLaser(world, traceDistance, (BlockHitResult) rayTraceResult, player);

            tag.putInt("timeBeforeCooling", 0);
            temperature++;
            tag.putInt("temperature", temperature);

        } else {
            setInUse(false, tag);
            setDistance(0, tag);
            tag.putBoolean("isOverheated", true);
            tag.putInt("cooldownTimer", 0);
            //player.displayClientMessage(Component.literal("!!! Перегрев !!!"), true);
            stopLoopingSound();
            playCooldownSound(player);
        }
        //player.displayClientMessage(Component.literal(String.valueOf(n)), true);
    }
    private void breakBlockByLaser(Level world, double traceDistance, BlockHitResult rayTraceResult, Player player) {
        if (traceDistance <= range) {
            BlockPos targetPos = rayTraceResult.getBlockPos();
            BlockState blockState = world.getBlockState(targetPos);

            if (!blockState.isAir() && blockState.getDestroySpeed(world, targetPos) >= 0) {
                // Получаем прогресс ломания для текущего блока
                BlockBreakingProgress progress = breakingProgress.computeIfAbsent(player.getUUID(),
                        uuid -> new BlockBreakingProgress(targetPos, 0));

                // Если игрок нацелился на другой блок, сбрасываем прогресс
                if (!progress.pos.equals(targetPos)) {
                    progress.progress = 0; // сброс прогресса
                    progress.pos = targetPos; // обновляем позицию блока
                }

                // Увеличиваем прогресс для текущего блока
                progress.progress++;

                // Если прогресс не достиг максимума, продолжаем
                if (progress.progress < BREAK_TIME) {
                    // Отправляем пакет с прогрессом на клиент
                    if (!world.isClientSide()) {
                        S2CBreakBlockPacket packet = new S2CBreakBlockPacket(targetPos, (int) ((progress.progress / (float) BREAK_TIME) * 10));
                        PacketHandler.sendToAllClients(packet);
                    }
                } else {
                    // Разрушаем блок, если прогресс достиг максимума
                    if (!world.isClientSide()) {
                        // Разрушаем блок на сервере и удаляем прогресс
                        world.destroyBlock(targetPos, true, player);
                        breakingProgress.remove(player.getUUID());
                    }
                }
            }
        }
    }


    private static void damageEntities(Level world, LivingEntity entity, Vec3 look, double traceDistance, Vec3 start, BlockHitResult rayTraceResult) {
        AABB playerBox = entity.getBoundingBox().expandTowards(look.scale(traceDistance)).expandTowards(1.0D, 1.0D, 1.0D);
        for (Entity possible : world.getEntities(entity, playerBox)) {
            if (!(possible instanceof LivingEntity))return;
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
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        if (pLevel.isClientSide()) return;
        if (!pIsSelected) stopLoopingSound();
        Player player = (Player) pEntity;
        CompoundTag tag = pStack.getOrCreateTag();
        if (isInUse(tag) && !player.isUsingItem()) setInUse(false, tag);

        int temperature = tag.getInt("temperature");
        boolean isOverheated = tag.getBoolean("isOverheated");
        int cooldownTimer = tag.getInt("cooldownTimer");

        if (isOverheated) {
            tag.putInt("cooldownTimer", ++cooldownTimer);
            if (cooldownTimer >= COOLDOWN) {
                tag.putBoolean("isOverheated", false);
                tag.putInt("temperature", 0);
            }
        } else if (!player.isUsingItem() && temperature > 0) {
            int timeBeforeCooling = tag.getInt("timeBeforeCooling");
            if (timeBeforeCooling >= COOLDOWN_BEFORE_COOLING) {
                tag.putInt("temperature", Math.max(0, temperature - 1));
            } else {
                tag.putInt("timeBeforeCooling", timeBeforeCooling + 1);
            }
            if (temperature == 0) {
                tag.putInt("timeBeforeCooling", 0);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        if (pStack.hasTag()) {
            boolean isInUse = pStack.getTag().getBoolean("isInUse");
            boolean isOverheated = pStack.getTag().getBoolean("isOverheated");
            int temperature = pStack.getTag().getInt("temperature");
            int cooldownTimer = pStack.getTag().getInt("cooldownTimer");
            int timeBeforeCooling = pStack.getTag().getInt("timeBeforeCooling");
            pTooltipComponents.add(Component.literal("isInUse: " + isInUse));
            pTooltipComponents.add(Component.literal("isOverheated: " + isOverheated));
            pTooltipComponents.add(Component.literal("temperature: " + temperature));
            pTooltipComponents.add(Component.literal("cooldownTimer: " + cooldownTimer));
            pTooltipComponents.add(Component.literal("timeBeforeCooling: " + timeBeforeCooling));
        }
    }

    private void breakBlockInArea(Level world, Player player, BlockPos blockPos, int progress) {
        PacketHandler.sendToAllClients(new S2CBreakBlockPacket(blockPos, ((byte) progress)));
    }
    @OnlyIn(Dist.CLIENT)
    private boolean isLoopingSoundPlaying() {
        //System.out.println(loopingSound != null && Minecraft.getInstance().getSoundManager().isActive(loopingSound));
        return loopingSound != null && Minecraft.getInstance().getSoundManager().isActive(loopingSound);
    }
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        if (itemStack.getTag() != null) {
            this.setInUse(false, itemStack.getTag());
        }
        stopLoopingSound();
        return itemStack;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        if (itemStack.getTag() != null) {
            this.setInUse(false, itemStack.getTag());
        }
        stopLoopingSound();
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity livingEntity) {
        //this.setInUse(false);
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
            if (player.isUsingItem() && !isOverheated) {
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
        return UseAnim.CUSTOM;
    }
    public boolean isInUse(CompoundTag tag) {
        return tag.getBoolean("isInUse");
    }
    public void setInUse(boolean inUse, CompoundTag tag) {
        tag.putBoolean("isInUse", inUse);
    }
    public double getDistance(CompoundTag tag) {
        return tag.getDouble("distance");
    }
    public void setDistance(double distance, CompoundTag tag) {
        tag.putDouble("distance", distance);
    }
    public boolean isOverheated() {
        return isOverheated;
    }

    public int getTemperature() {
        return temperature;
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
