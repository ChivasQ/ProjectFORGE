package com.chivasss.pocket_dimestions.client;

import com.chivasss.pocket_dimestions.PocketDim;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

public class ShaderExample {
    private static final ResourceLocation DISTORT_SHADER = new ResourceLocation("shaders/post/notch.json");

    public static void applyDistortion() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.execute(() -> {
                try {
                    mc.gameRenderer.loadEffect(DISTORT_SHADER);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static void stopDistortion() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            mc.execute(() -> {
                try {
                    mc.gameRenderer.shutdownEffect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
}