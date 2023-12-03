package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.entity.client.BoreBlockEntityWithoutLevelRenderer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

    public class Bore extends Item {
    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return BoreBlockEntityWithoutLevelRenderer.instance;
            }
        });
    }

    public Bore(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player entity, InteractionHand hand) {
        entity.playSound(SoundEvents.SPYGLASS_USE, 1.0F, 1.0F);
        ItemStack itemstack = entity.getItemInHand(hand);
        return InteractionResultHolder.consume(itemstack);
    }



    public int getUseDuration(ItemStack p_42933_) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack p_40935_) {
        return UseAnim.BOW;
    }

    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        this.stopUsing(livingEntity);
        return itemStack;
    }

    public void releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        this.stopUsing(livingEntity);
    }

    private void stopUsing(LivingEntity livingEntity) {
        livingEntity.playSound(SoundEvents.SPYGLASS_STOP_USING, 1.0F, 1.0F);
    }

}
