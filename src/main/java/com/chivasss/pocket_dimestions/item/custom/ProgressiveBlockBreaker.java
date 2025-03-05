package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.UUID;

@Mod.EventBusSubscriber
public class ProgressiveBlockBreaker extends Item {
    private static final HashMap<UUID, BlockBreakingProgress> breakingProgress = new HashMap<>();
    private static final int BREAK_TIME = 20; // 1 секунда (20 тиков)

    public ProgressiveBlockBreaker(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        if (!level.isClientSide) {
            BlockPos targetPos = ((BlockHitResult) player.pick(5, 0, false)).getBlockPos();
            BlockState targetState = level.getBlockState(targetPos);

            if (!targetState.isAir()) {
                breakingProgress.put(player.getUUID(), new BlockBreakingProgress(targetPos, 0));
            }
        }
        return super.use(level, player, hand);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        if (!breakingProgress.containsKey(player.getUUID())) return;

        BlockBreakingProgress progress = breakingProgress.get(player.getUUID());
        BlockPos pos = progress.pos;
        Level level = player.level();

        BlockPos targetPos = ((BlockHitResult) player.pick(5, 0, false)).getBlockPos();
        if (!targetPos.equals(pos) || player.getMainHandItem().getItem() != ModItems.PROGRESSIVE_BREAKER.get()) {
            if (player.level().isClientSide())  level.destroyBlockProgress(player.getId(), pos, -1); // Сбрасываем анимацию
            breakingProgress.remove(player.getUUID());
            return;
        }

        progress.progress++;
        if (player.level().isClientSide()) level.destroyBlockProgress(player.getId(), pos, (int) ((progress.progress / (float) BREAK_TIME) * 10));
        player.displayClientMessage(Component.literal(String.valueOf((progress.progress / (float) BREAK_TIME) * 10)), true);

        if (!player.level().isClientSide() && progress.progress >= BREAK_TIME) {
            level.destroyBlock(pos, true, player);
            breakingProgress.remove(player.getUUID());
        }
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
