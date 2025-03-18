package com.chivasss.pocket_dimestions.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.util.Mth.lerp;
import static net.minecraft.util.Mth.randomBetween;

public class AdrenalineOverlay {
    protected static final ResourceLocation TEXTURE = new ResourceLocation(PocketDim.MODID, "textures/misc/lethal_mask.png");
    private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");
    protected final Minecraft minecraft;
    private static final Minecraft minecraft1 = Minecraft.getInstance();
    private static final RandomSource RANDOM_SOURCE = RandomSource.create();


    public static final IGuiOverlay OVERLAY_ADRENALINE = ((gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        ItemStack itemstack = mc.player.getInventory().getArmor(3);



        if (itemstack.is(Items.DIAMOND_HELMET) && mc.options.getCameraType().isFirstPerson()) {
            if (mc.screen != null) {
                return;
            } else ShaderExample.stopDistortion();
            ShaderExample.applyDistortion();
            // TODO: play with shaders
            //renderTextureOverlay(guiGraphics, TEXTURE, 1.0F, screenWidth, screenHeight);

        } else ShaderExample.stopDistortion();
        if (itemstack.is(Items.IRON_HELMET) && mc.options.getCameraType().isFirstPerson()) {
            renderConfusionOverlay(guiGraphics, 1.0F, screenWidth, screenHeight);
        }
        if (itemstack.is(Items.GOLDEN_HELMET)) {
            //renderConfusionOverlay(guiGraphics, 1.0F, screenWidth, screenHeight);

            minecraft1.getCameraEntity().setYRot(minecraft1.getCameraEntity().getYRot());
            //minecraft1.getCameraEntity().yo = minecraft1.player.position().y + Mth.randomBetween(RANDOM_SOURCE, -.5F, .5F);
            //minecraft1.getCameraEntity().zo = minecraft1.player.position().z + Mth.randomBetween(RANDOM_SOURCE, -.5F, .5F);
            minecraft1.player.yHeadRot = 90;
            minecraft1.player.yHeadRotO = 90;
        }

    });

    public AdrenalineOverlay(Minecraft minecraft) {
        this.minecraft = minecraft;
    }

    protected static void renderTextureOverlay(GuiGraphics pGuiGraphics, ResourceLocation pShaderLocation, float pAlpha, int screenWidth, int screenHeight) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
        pGuiGraphics.blit(pShaderLocation, 0, 0, -90, 0.0F, 0.0F, screenWidth, screenHeight, screenWidth, screenHeight);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
    private static void renderConfusionOverlay(GuiGraphics pGuiGraphics, float pAlpha, int screenWidth, int screenHeight) {
            if (pAlpha < 1.0F) {
                pAlpha *= pAlpha;
                pAlpha *= pAlpha;
                pAlpha = pAlpha * 0.8F + 0.2F;
            }

            RenderSystem.disableDepthTest();
            RenderSystem.depthMask(false);
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, pAlpha);
            TextureAtlasSprite textureatlassprite = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(Blocks.NETHER_PORTAL.defaultBlockState());
            pGuiGraphics.blit(0, 0, -90, screenWidth, screenHeight, textureatlassprite);
            RenderSystem.depthMask(true);
            RenderSystem.enableDepthTest();
            pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);

        }

}
