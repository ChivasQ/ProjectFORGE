package com.chivasss.pocket_dimestions.screen;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class AltarColumnScreen extends AbstractContainerScreen<AltarColumnMenu> {
    private static final ResourceLocation TEXTURE =
            new ResourceLocation(PocketDim.MODID, "textures/gui/boot_test.png");
    private static final ResourceLocation TEXTURE_BG =
            new ResourceLocation(PocketDim.MODID, "textures/gui/bg.png");
    private static final ResourceLocation TEXTURE_FRAME =
            new ResourceLocation(PocketDim.MODID, "textures/gui/frame.png");

    private boolean dragging = false;
    private int dragStartX, dragStartY;
    private int imageOffsetX = 0, imageOffsetY = 0;


    public AltarColumnScreen(AltarColumnMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.inventoryLabelY = 1000;
        this.titleLabelY = 1000;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);

        imageWidth = 256;
        imageHeight = 256;

        int x = (width - imageWidth)/ 2;
        int y = (height - imageHeight)/ 2;

        pGuiGraphics.drawString(
                this.font,
                String.format("Offset: X=%d, Y=%d Mouse: X=%d, Y=%d", imageOffsetX, imageOffsetY, pMouseX - x , pMouseY - y),
                x, y-10,
                0xFFFFFF,
                false
        );
        //renderProgress(pGuiGraphics, x, y);

        renderBg(pGuiGraphics, x, y,-imageOffsetX, -imageOffsetY);
        renderFrame(pGuiGraphics, imageOffsetX + x + imageWidth/2, imageOffsetY + y + imageHeight/2, x, y, pMouseX, pMouseY);

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
    }

    private void renderProgress(GuiGraphics graphics, int x, int y){
        if (menu.isCrafting()){
            graphics.blit(TEXTURE,x+10, y+9, 176,0,9, menu.getScaledProgress());
        }
    }
    private void renderBg(GuiGraphics graphics, int x, int y, int pMouseX, int pMouseY){
        if (menu.isCrafting()){
            graphics.blit(TEXTURE_BG,x+10, y+9, pMouseX,pMouseY,235, 238);
        }
    }
    private void renderFrame(GuiGraphics graphics, int imageOffsetX, int imageOffsetY, int x, int y, int pMouseX, int pMouseY){
        if (menu.isCrafting()) {
            int textureSize = 24;
            int clipX = x + 10;
            int clipY = y + 9;
            int clipWidth = imageWidth - 20;
            int clipHeight = imageHeight - 18;

            int screenHeight = Minecraft.getInstance().getWindow().getHeight();
            double scale = Minecraft.getInstance().getWindow().getGuiScale();

            int scissorX = (int)(clipX * scale);
            int scissorY = (int)((screenHeight / scale - clipY - clipHeight) * scale);
            int scissorW = (int)(clipWidth * scale);
            int scissorH = (int)(clipHeight * scale);

            RenderSystem.enableScissor(scissorX, scissorY, scissorW, scissorH);

            graphics.blit(TEXTURE_FRAME, imageOffsetX, imageOffsetY, 0, 0, textureSize, textureSize, textureSize, textureSize);
            graphics.renderItem(ModItems.CHALK.get().getDefaultInstance(), imageOffsetX + 4, imageOffsetY + 4);

//            if (isHovering(0, 0, 100, 100, pMouseX - x , pMouseY - y)) {
//                graphics.renderTooltip(this.font, ModItems.CHALK.get().getDefaultInstance(), imageOffsetX, imageOffsetY);
//            }

            RenderSystem.disableScissor();
        }

    }


    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            dragging = true;
            dragStartX = (int) mouseX - imageOffsetX;
            dragStartY = (int) mouseY - imageOffsetY;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            dragging = false;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging && button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            imageOffsetX = (int) mouseX - dragStartX;
            imageOffsetY = (int) mouseY - dragStartY;
        }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    protected boolean isHovering(int pX, int pY, int pWidth, int pHeight, double pMouseX, double pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pMouseX -= (double)i;
        pMouseY -= (double)j;
        return pMouseX >= (double)(pX - 1) && pMouseX < (double)(pX + pWidth + 1) && pMouseY >= (double)(pY - 1) && pMouseY < (double)(pY + pHeight + 1);
    }
}
