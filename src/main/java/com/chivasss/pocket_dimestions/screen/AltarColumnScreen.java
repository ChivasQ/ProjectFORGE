package com.chivasss.pocket_dimestions.screen;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.item.ModItems;
import com.chivasss.pocket_dimestions.sound.ModSounds;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

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
    private final List<ResearchNode> nodes = new ArrayList<>();


    public AltarColumnScreen(AltarColumnMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.imageOffsetX = 469;
        this.imageOffsetY = 243;
        this.inventoryLabelY = 1000;
        this.titleLabelY = 1000;

        if (nodes.isEmpty()) {
            ResearchNode node0 = new ResearchNode(0, 0, "node0!", ModItems.EIGTH_BALL.get().getDefaultInstance(), true);
            ResearchNode node1 = new ResearchNode(100, 0, "node1!", ModItems.ZINC.get().getDefaultInstance(), true).connectTo(node0);
            ResearchNode node2 = new ResearchNode(100, 90, "node2!", ModItems.CHALK.get().getDefaultInstance(), true).connectTo(node0);
            ResearchNode node3 = new ResearchNode(150, 150, "node3!", ModItems.BORE.get().getDefaultInstance(), true).connectTo(node2);
            nodes.add(node0);
            nodes.add(node1);
            nodes.add(node2);
            nodes.add(node3);
        }
        Minecraft.getInstance().player.playSound(ModSounds.RESEARCH_BOOR_OPEN.get(), 1.0F, 1.0F);
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

        renderBg(pGuiGraphics, x, y,-imageOffsetX, -imageOffsetY);
        //renderFrame(pGuiGraphics, imageOffsetX + x + imageWidth/2, imageOffsetY + y + imageHeight/2, x, y, pMouseX, pMouseY);
        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderResearchTree(pGuiGraphics, imageOffsetX, imageOffsetY, pMouseX, pMouseY, x, y);
    }

    private void renderResearchTree(GuiGraphics graphics, int baseX, int baseY, int mouseX, int mouseY, int x, int y) {
        for (ResearchNode node : nodes) {
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
            int nodeX = baseX + node.xPos;
            int nodeY = baseY + node.yPos;

            RenderSystem.enableScissor(scissorX, scissorY, scissorW, scissorH);

            graphics.blit(TEXTURE_FRAME, nodeX-4, nodeY-4, 0, 0, textureSize, textureSize, textureSize, textureSize);
            graphics.renderItem(node.itemToDisplay, nodeX, nodeY);

            if (mouseX >= nodeX && mouseX < nodeX + 16 && mouseY >= nodeY && mouseY < nodeY + 16) {
                graphics.renderTooltip(this.font, Component.literal(node.description), mouseX, mouseY);
            }

            renderConnections(graphics, baseX, baseY);

            RenderSystem.disableScissor();
        }
    }

    private void renderConnections(GuiGraphics graphics, int baseX, int baseY) {
        for (ResearchNode node : nodes) {
            int startX = baseX + node.xPos + 8; // центр иконки
            int startY = baseY + node.yPos + 8;

            for (ResearchNode target : node.connectedTo) {
                int endX = baseX + target.xPos + 8;
                int endY = baseY + target.yPos + 8;
                if (target.isUnlocked) {
                    drawLine(graphics, startX, startY, endX, endY, 0xFFFFFFFF);
                }
            }
        }
    }

    private void drawLine(GuiGraphics graphics, int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y1 - y2;

        if (Math.abs(dx) > Math.abs(dy)) {
            float slope = dy / (float) dx;
            for (int i = 0; i <= Math.abs(dx); i++) {
                int x = x1 + (dx > 0 ? i : -i);
                int y = y1 + Math.round(i * slope);
                graphics.fill(x, y, x + 1, y + 1, color);
            }
        } else {
            float slope = dx / (float) dy;
            for (int i = 0; i <= Math.abs(dy); i++) {
                int y = y1 + (dy > 0 ? -i : i);
                int x = x1 + Math.round(i * slope);
                graphics.fill(x, y, x + 1, y + 1, color);
            }
        }
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
