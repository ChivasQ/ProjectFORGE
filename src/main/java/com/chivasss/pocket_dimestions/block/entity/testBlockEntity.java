package com.chivasss.pocket_dimestions.block.entity;

import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class testBlockEntity  extends BlockEntity {
    private final boolean[][] pixels = new boolean[16][16];

    public testBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntites.TEST_BLOCK_ENTITY.get(), pPos, pBlockState);
    }


    public void setPixel(int x, int y, boolean value) {
        if (x >= 0 && x < 16 && y >= 0 && y < 16) {
            pixels[x][y] = value;
            markDirty();
        }
    }
    public void clear() {
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                pixels[x][y] = false;
            }
        }
        markDirty();
    }
    public boolean[][] getPixels() {
        return pixels;
    }

    private void markDirty() {
        // стандартное обновление блока и клиентского состояния
        if (level != null && !level.isClientSide) {
            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    @Override
    protected void saveAdditional(net.minecraft.nbt.CompoundTag tag) {
        super.saveAdditional(tag);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                tag.putBoolean("pixel_" + x + "_" + y, pixels[x][y]);
            }
        }
    }

    @Override
    public void load(net.minecraft.nbt.CompoundTag tag) {
        super.load(tag);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                pixels[x][y] = tag.getBoolean("pixel_" + x + "_" + y);
            }
        }
    }

    public boolean checkPixelClosure() {
        boolean[][] visited = new boolean[16][16];
        boolean foundStart = false;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if (pixels[x][y]) {
                    foundStart = true;
                    dfs(x, y, visited);
                    break;
                }
            }
            if (foundStart) break;
        }

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if (pixels[x][y] && !visited[x][y]) {
                    return false;
                }
            }
        }

        return true;
    }

    private void dfs(int x, int y, boolean[][] visited) {
        if (x < 0 || x >= 16 || y < 0 || y >= 16 || visited[x][y] || !pixels[x][y]) {
            return;
        }

        visited[x][y] = true;

        dfs(x - 1, y, visited);
        dfs(x + 1, y, visited);
        dfs(x, y - 1, visited);
        dfs(x, y + 1, visited);
    }
    public boolean checkIfCircle() {
        int centerX = 7;
        int centerY = 7;

        double totalDistance = 0;
        int activePixelCount = 0;
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if (pixels[x][y]) { // Если пиксель активен
                    double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    totalDistance += distance;
                    activePixelCount++;
                }
            }
        }

        if (activePixelCount == 0) return false;

        double averageDistance = totalDistance / activePixelCount;

        double tolerance = 2.5;

        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                if (pixels[x][y]) {
                    double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    if (Math.abs(distance - averageDistance) > tolerance) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

}
