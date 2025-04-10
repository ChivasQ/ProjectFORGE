package com.chivasss.pocket_dimestions.item.custom;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.util.StructureUtils;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class StructCreatorItem extends Item {
    public StructCreatorItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        //pLevel.addParticle(ParticleTypes.FALLING_OBSIDIAN_TEAR, pPos.getX(), pPos.getY(), pPos.getZ(), 0, 0.1, 0);
        if (pLevel.isClientSide()) return super.useOn(pContext);

        ResourceLocation structureID = new ResourceLocation(PocketDim.MODID, "modfurnace");
        StructureTemplateManager manager = pLevel.getServer().getStructureManager();

//        Optional<StructureTemplate> optionalTemplate = manager.get(structureID);
//        if (optionalTemplate.isPresent()) {
//            StructureTemplate structure = optionalTemplate.get();
//
//
//            StructurePlaceSettings settings = new StructurePlaceSettings()
//                    .setMirror(Mirror.NONE)
//                    .setRotation(Rotation.NONE)
//                    .setIgnoreEntities(false);
//
//            structure.placeInWorld((ServerLevelAccessor) pLevel, pPos.north(2).below(1).west(1), pPos.north(2).below(1).west(1), settings, pLevel.random, 3);
//            for (int x = -1; x < structure.getSize().getX(); x++) {
//                for (int y = -1; y < structure.getSize().getY(); y++) {
//                    for (int z = -2; z < structure.getSize().getZ(); z++) {
//                        BlockPos blockPos = pPos.offset(x, y, z);
//                        pLevel.sendBlockUpdated(blockPos, pLevel.getBlockState(blockPos), pLevel.getBlockState(blockPos), 3);
//                    }
//                }
//            }
//        }
        StructureUtils structureUtils = new StructureUtils();
        CompoundTag modFurnace = structureUtils.readNBTFromResource(pContext.getLevel().getServer(), "mod_furnace_structure");
        System.out.println(modFurnace != null);
        structureUtils.loadBlocksFromNBT(modFurnace, pPos, pLevel);
        //StructureUtils.build(pPos, pLevel);
        BlockPos structureValidator = structureUtils.structureValidator(pPos, pLevel);
        if (structureValidator != null) {
            StructureUtils structureUtils1 = new StructureUtils();
            System.out.println("s");
            CompoundTag modFurnace1 = structureUtils1.readNBTFromResource(pContext.getLevel().getServer(), "mod_furnace_multiblock");
            structureUtils1.loadBlocksFromNBT(modFurnace1, structureValidator, pLevel);
            structureUtils1.build(structureValidator, pLevel);
            pLevel.playSound(null, pPos, SoundEvents.ANVIL_USE, SoundSource.PLAYERS, 0.7f, .5f); // or set pitch to 5.0f idk
        }

        return InteractionResult.SUCCESS;
    }
}
