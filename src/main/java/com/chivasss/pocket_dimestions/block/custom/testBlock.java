package com.chivasss.pocket_dimestions.block.custom;

import com.chivasss.pocket_dimestions.block.entity.ModBlockEntites;
import com.chivasss.pocket_dimestions.block.entity.testBlockEntity;
import com.chivasss.pocket_dimestions.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

public class testBlock extends BaseEntityBlock {
    public testBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        //if (pLevel.isClientSide()) return InteractionResult.FAIL;


        BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
        if (blockEntity instanceof testBlockEntity testBE) {
            ClipContext context = new ClipContext(
                    pPlayer.getEyePosition(1.0F),
                    pPlayer.getEyePosition(1.0F).add(pPlayer.getLookAngle().scale(5.0D)),
                    ClipContext.Block.OUTLINE,
                    ClipContext.Fluid.NONE,
                    pPlayer
            );

            BlockHitResult result = pLevel.clip(context);
            if (result.getType() == HitResult.Type.BLOCK) {
                BlockPos hitPos = result.getBlockPos();
                Direction face = result.getDirection();
                if (face == Direction.UP && pPlayer.getItemInHand(pHand).getItem() == ModItems.CHALK.get()) {
                    Vec3 hitVec = result.getLocation();
                    double localX = hitVec.x - hitPos.getX();
                    double localZ = hitVec.z - hitPos.getZ();
                    int pixelX = (int) (localX * 16);
                    int pixelZ = (int) (localZ * 16);

//                    testBE.setPixel(pixelX, pixelZ+1, true);
//                    testBE.setPixel(pixelX+1, pixelZ, true);
                    testBE.setPixel(pixelX, pixelZ, true);
//                    testBE.setPixel(pixelX, pixelZ-1, true);
//                    testBE.setPixel(pixelX-1, pixelZ, true);

                    pPlayer.sendSystemMessage(Component.literal("Pixel set at: " + pixelX + ", " + pixelZ));
                } else if (face == Direction.UP && pPlayer.getItemInHand(pHand).getItem() == Items.WATER_BUCKET.asItem()) {
                    testBE.clear();
                } else if (pPlayer.getItemInHand(pHand).getItem() == Items.STICK.asItem()) {
                    if(testBE.checkIfCircle())
                        pLevel.addParticle(ParticleTypes.FLAME, true, pPos.getX() + 0.5,pPos.getY() + 1, pPos.getZ() + 0.5, 0,1,0);;
                }
            }
        }
        return InteractionResult.CONSUME;
    }




    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return ModBlockEntites.TEST_BLOCK_ENTITY.get().create(pPos, pState);
    }
}
