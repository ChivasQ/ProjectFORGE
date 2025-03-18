package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.entity.client.BoreItemRenderer;
import com.chivasss.pocket_dimestions.world.dimension.DimTeleporter;
import com.chivasss.pocket_dimestions.world.dimension.ModDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.function.Consumer;

public class TestItem1 extends Item {
    public TestItem1(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        MinecraftServer server = pLevel.getServer();
        if (!pLevel.isClientSide()) {
            if (server != null) {
                if (pLevel.dimension() == ModDimensions.POCKET_LEVEL_KEY) {
                    ServerLevel overWorld = server.getLevel(Level.OVERWORLD);
                    if (overWorld != null) {
                        pPlayer.changeDimension(overWorld, new DimTeleporter(pPlayer.getOnPos(), false));
                    }
                } else {
                    ServerLevel dim = server.getLevel(ModDimensions.POCKET_LEVEL_KEY);
                    if (dim != null) {
                        pPlayer.changeDimension(dim, new DimTeleporter(pPlayer.getOnPos(), false));
                    }
                }
                pPlayer.getCooldowns().addCooldown(this, 10);
            }
        }

        pPlayer.startUsingItem(pHand);
        return InteractionResultHolder.consume(itemstack);
    }


}
