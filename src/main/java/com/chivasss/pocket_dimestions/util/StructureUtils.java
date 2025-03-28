package com.chivasss.pocket_dimestions.util;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.block.entity.MultiBlockFillerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.apache.commons.lang3.tuple.ImmutablePair;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StructureUtils {
    private List<ImmutablePair<Integer, Vec3i>> blockPos;
    private List<Block> blockList;
    private CompoundTag structureNBT;

    public StructureUtils() {
        blockList = new ArrayList<>();
        blockPos = new ArrayList<>();
        structureNBT = new CompoundTag();
    }


    public CompoundTag readNBTFromResource(MinecraftServer server, String structureName) {
        ResourceManager resourceManager = server.getResourceManager();
        ResourceLocation structurePath = new ResourceLocation(PocketDim.MODID, "structures/" + structureName + ".nbt");

        try {
            Optional<Resource> optionalResource = resourceManager.getResource(structurePath);
            if (optionalResource.isPresent()) {
                InputStream inputStream = optionalResource.get().open();
                structureNBT = NbtIo.readCompressed(inputStream);
                return structureNBT;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Structure not found: " + structurePath);
        return null;
    }
    public void loadBlocksFromNBT(CompoundTag structureNBT, BlockPos startPos, Level world) {
        if (structureNBT == null || !structureNBT.contains("blocks", Tag.TAG_LIST)) {
            System.out.println("File do not contain any blocks.");
            return;
        }

        blockPos = new ArrayList<>();
        blockList = new ArrayList<>();

        ListTag blocks = structureNBT.getList("blocks", Tag.TAG_COMPOUND);
        for (Tag tag : blocks) {
            if (tag instanceof CompoundTag blockTag) {
                ListTag posList = blockTag.getList("pos", Tag.TAG_INT);
                int x = posList.getInt(0);
                int y = posList.getInt(1);
                int z = posList.getInt(2);

                int blockName = blockTag.getInt("state");
                blockPos.add(new ImmutablePair<>(blockName, new Vec3i(x, y, z)));
            }
        }

        ListTag palette = structureNBT.getList("palette", Tag.TAG_COMPOUND);
        for (Tag tag : palette) {
            if (tag instanceof CompoundTag paletteTag) {
                String palleteList = paletteTag.getString("Name");
                blockList.add(BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(palleteList)).orElse(null));
            }
        }
    }


    private <T extends Comparable<T>> BlockState applyProperty(BlockState state, Property<T> property, String value) {
        Optional<T> optionalValue = property.getValue(value);
        return optionalValue.map(val -> state.setValue(property, val)).orElse(state);
    }

    private CompoundTag getPropertiesFromNBT(int index) {
        if (index < 0 || index >= blockList.size()) return null;
        CompoundTag tag = new CompoundTag();

        ListTag palette = structureNBT.getList("palette", Tag.TAG_COMPOUND);
        CompoundTag blockTag = palette.getCompound(index);

        if (blockTag.contains("Properties", Tag.TAG_COMPOUND)) {
            return blockTag.getCompound("Properties");
        }
        return null;
    }

    public void build(BlockPos startPos, Level world) {
        for (ImmutablePair<Integer, Vec3i> block : blockPos) {
            Block blockInstance = blockList.get(block.left);
            if (blockInstance == null) continue;

            BlockState state = blockInstance.defaultBlockState();
            CompoundTag blockProperties = getPropertiesFromNBT(block.left);

            if (blockProperties != null) {
                for (String key : blockProperties.getAllKeys()) {
                    Property<?> property = blockInstance.getStateDefinition().getProperty(key);
                    if (property != null) {
                        state = applyProperty(state, property, blockProperties.getString(key));
                    }
                }
            }

            BlockPos pos = startPos.offset(block.right);

            world.setBlock(pos, state, 3);

            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof MultiBlockFillerEntity) {
                MultiBlockFillerEntity multiBlockFiller = (MultiBlockFillerEntity) blockEntity;

                multiBlockFiller.setStoredBlock(blockInstance.defaultBlockState());  // Например, устанавливаем хранимый блок
                multiBlockFiller.setRelativePosition(new BlockPos(block.right.getX(), block.right.getY(), block.right.getZ()));

                multiBlockFiller.setChanged();
                world.sendBlockUpdated(pos, state, state, 3);
            }
            world.sendBlockUpdated(pos, state, state, 3);
        }
    }
    public boolean structureValidator(BlockPos startPos, Level world) {
        for (ImmutablePair<Integer, Vec3i> block : blockPos) {
            Block blockInstance = blockList.get(block.left);
            if (blockInstance == null) continue;
            BlockState state = blockInstance.defaultBlockState();

            BlockPos pos = startPos.offset(block.right);
            if(!(world.getBlockState(pos).getBlock().defaultBlockState() == state)) return false;
        }
        return true;
    }

}
