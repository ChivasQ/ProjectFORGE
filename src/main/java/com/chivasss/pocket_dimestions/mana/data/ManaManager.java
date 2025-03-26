package com.chivasss.pocket_dimestions.mana.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ManaManager extends SavedData {

    private final Map<ChunkPos, Mana> manaMap = new HashMap<>();
    private final Random random = new Random();
    @NotNull
    public static ManaManager get(Level level) {
        if (level.isClientSide()) {
            throw new RuntimeException("ac");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
    }
    @NotNull
    private Mana getManaInternal(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return manaMap.computeIfAbsent(chunkPos, cp -> new Mana(random.nextInt(100))); //TODO: make noise generator
    }

    public int getMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        setDirty();
        return mana.getMana();
    }

    public int setMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        int present = mana.getMana();
        if (present > 0) {
            mana.setMana(present - 1);
            setDirty();
            return 1;
        }
        return 0;
    }


    public ManaManager() {

    }

    public ManaManager(CompoundTag tag) {
        ListTag listTag = tag.getList("mana", Tag.TAG_COMPOUND);
        for (Tag t : listTag) {
            CompoundTag manaTag = (CompoundTag) t;
            Mana mana = new Mana(manaTag.getInt("mana"));
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
            manaMap.put(pos, mana);
        }

    }
    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag listTag = new ListTag();
        manaMap.forEach((chunkPos, mana) -> {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", chunkPos.x);
            manaTag.putInt("z", chunkPos.z);
            manaTag.putInt("mana", mana.getMana());
            listTag.add(manaTag);
        });
        tag.put("mana", listTag);
        return tag;
    }
}
