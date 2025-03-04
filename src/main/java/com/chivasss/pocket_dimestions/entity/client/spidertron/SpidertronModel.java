package com.chivasss.pocket_dimestions.entity.client.spidertron;

// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SpidertronModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart main;
    private final ModelPart base;
    private final ModelPart bone1;
    private final ModelPart leg1;
    private final ModelPart sub_leg1;



    public SpidertronModel(ModelPart root) {
        this.main = root.getChild("main");
        this.base = main.getChild("base");
        this.bone1 = main.getChild("bone1");
        this.leg1 = bone1.getChild("leg1");
        this.sub_leg1 = leg1.getChild("sub_leg1");

    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offsetAndRotation(16.0F, 24.0F, -16.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r1 = main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -3.0F, -6.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9063F, -5.0F, -15.9063F, 0.0F, 0.0F, 0.4363F));

        PartDefinition base = main.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0).addBox(-14.6274F, -8.5F, -8.0F, 13.2548F, 2.0F, 32.0F, new CubeDeformation(0.0F))
                .texOffs(58, 0).addBox(-24.0F, -8.5F, 1.3726F, 32.0F, 2.0F, 13.2548F, new CubeDeformation(0.0F)), PartPose.offset(-7.9063F, 6.0F, -23.9063F));

        PartDefinition octagon_r1 = base.addOrReplaceChild("octagon_r1", CubeListBuilder.create().texOffs(58, 15).addBox(-16.0F, -0.5F, -6.6274F, 32.0F, 2.0F, 13.2548F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-6.6274F, -0.5F, -16.0F, 13.2548F, 2.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -8.0F, 8.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone1 = main.addOrReplaceChild("bone1", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.9063F, 43.0F, -15.9063F, 0.0F, 3.1416F, 0.0F));

        PartDefinition leg1 = bone1.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(64, 68).addBox(-1.0F, -31.0F, -1.0F, 2.0F, 32.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.0F, -16.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition sub_leg1 = leg1.addOrReplaceChild("sub_leg1", CubeListBuilder.create().texOffs(16, 68).addBox(-1.0F, -55.0F, -1.0F, 2.0F, 54.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -30.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return this.main;
    }
}