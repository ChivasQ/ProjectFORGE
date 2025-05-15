package com.chivasss.pocket_dimestions.screen;

import com.chivasss.pocket_dimestions.block.ModBlocks;
import com.chivasss.pocket_dimestions.block.entity.AltarColumnBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class AltarColumnMenu extends AbstractContainerMenu {
    public final AltarColumnBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;


    protected AltarColumnMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
        this(pContainerId, inventory, inventory.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(4));
    }

    public AltarColumnMenu(int pContainerId, Inventory inventory, BlockEntity blockEntity, ContainerData data){
        super(ModMenuTypes.ALTAR_COLUMN_MENU.get(), pContainerId);
        checkContainerSize(inventory, 4);
        this.blockEntity = ((AltarColumnBlockEntity) blockEntity);
        this.level = inventory.player.level();
        this.data = data;

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 79, 33));
            this.addSlot(new SlotItemHandler(iItemHandler, 1, 79, 5));
            this.addSlot(new SlotItemHandler(iItemHandler, 2, 103, 49));
            this.addSlot(new SlotItemHandler(iItemHandler, 3, 56, 49));
        });

        addDataSlots(data);
    }

    public boolean isCrafting(){
        return true;
    }

    public int getScaledProgress() {
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int progressArrowSize = 66;
        return maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ALTAR.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot (playerInventory, i, 8 + i * 18,142));
        }
    }


}
