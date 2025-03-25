package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.world.chunk.ChunkManaManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.BlockHitResult;

public class TestChunkManaBlock extends Block {
    public TestChunkManaBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (pLevel.isClientSide()) return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        LevelChunk chunk = pLevel.getChunkAt(pPos);
        int mana = ChunkManaManager.getMana(chunk);
        pPlayer.sendSystemMessage(Component.literal("Mana in this chunk: " + mana));
        ChunkManaManager.setMana(chunk, mana + 10);
        pPlayer.sendSystemMessage(Component.literal("Mana in this chunk: " + ChunkManaManager.getMana(chunk)));
        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
