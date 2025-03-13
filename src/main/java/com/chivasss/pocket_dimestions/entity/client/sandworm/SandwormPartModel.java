package com.chivasss.pocket_dimestions.entity.client.sandworm;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports



public class SandwormPartModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart part1;
    private final ModelPart blade0;
    private final ModelPart blade1;
    private final ModelPart blade2;
    private final ModelPart blade3;

    public SandwormPartModel(ModelPart root) {
        this.part1 = root.getChild("part1");
        this.blade0 = this.part1.getChild("blade0");
        this.blade1 = this.part1.getChild("blade1");
        this.blade2 = this.part1.getChild("blade2");
        this.blade3 = this.part1.getChild("blade3");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition part1 = partdefinition.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(0, 34).addBox(-7.875F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.325F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.125F, 24.0F, 0.25F, 1.5708F, 0.0F, 0.0F));

        PartDefinition blade0 = part1.addOrReplaceChild("blade0", CubeListBuilder.create(), PartPose.offset(0.125F, 0.0F, 0.0F));

        PartDefinition banana_r1 = blade0.addOrReplaceChild("banana_r1", CubeListBuilder.create().texOffs(32, 85).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 85).addBox(-13.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r2 = blade0.addOrReplaceChild("banana_r2", CubeListBuilder.create().texOffs(64, 34).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(64, 34).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade1 = part1.addOrReplaceChild("blade1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition banana_r3 = blade1.addOrReplaceChild("banana_r3", CubeListBuilder.create().texOffs(32, 85).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 85).addBox(-13.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r4 = blade1.addOrReplaceChild("banana_r4", CubeListBuilder.create().texOffs(64, 34).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(64, 34).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade2 = part1.addOrReplaceChild("blade2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition banana_r5 = blade2.addOrReplaceChild("banana_r5", CubeListBuilder.create().texOffs(32, 85).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 85).addBox(-13.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r6 = blade2.addOrReplaceChild("banana_r6", CubeListBuilder.create().texOffs(64, 34).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(64, 34).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade3 = part1.addOrReplaceChild("blade3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition banana_r7 = blade3.addOrReplaceChild("banana_r7", CubeListBuilder.create().texOffs(32, 85).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 85).addBox(-13.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r8 = blade3.addOrReplaceChild("banana_r8", CubeListBuilder.create().texOffs(64, 34).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(64, 34).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(68, 0).addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.part1.getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
    }
    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
//        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
//        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
//        this.tower.setRotation(0,0,0);
//        this.turret_main.setRotation(0,0,0);


        this.part1.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.part1.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }
    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        part1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}