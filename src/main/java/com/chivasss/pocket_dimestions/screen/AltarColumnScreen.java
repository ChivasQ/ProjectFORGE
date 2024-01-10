package com.chivasss.pocket_dimestions.screen;

import com.chivasss.pocket_dimestions.PocketDim;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AltarColumnScreen extends AbstractContainerScreen<AltarColumnMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PocketDim.MODID, "textures/gui/altar_column_gui.png");

    public AltarColumnScreen(AltarColumnMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        //this.inventoryLabelY = 1000;
        //this.titleLabelY = 1000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth)/2;
        int y = (height - imageHeight)/2;

        pGuiGraphics.blit(TEXTURE, x, y,0, 0, imageWidth, imageHeight);

        renderProgress(pGuiGraphics, x, y);


    }
    private void renderProgress(GuiGraphics graphics, int x, int y){
        if (menu.isCrafting()){
            graphics.blit(TEXTURE,x+8, y+9, 176,0,9, menu.getScaledProgress());
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
