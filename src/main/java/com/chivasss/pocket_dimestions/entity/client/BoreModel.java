package com.chivasss.pocket_dimestions.entity.client;


import com.chivasss.pocket_dimestions.PocketDim;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BoreModel<T extends Entity> extends Model {
    private final ModelPart body;
    private final ModelPart muzzle;;

    public BoreModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.body = root.getChild("body");
        this.muzzle = root.getChild("muzzle");
    }

    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 47).addBox(-1.0F, 1.9688F, -9.6297F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(54, 47).addBox(-2.0F, 1.9688F, -15.6297F, 4.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(32, 33).addBox(-1.0F, -6.0312F, -16.6297F, 2.0F, 1.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(20, 55).addBox(-1.0F, -5.0312F, -15.1297F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-1.0F, 0.9688F, -1.6297F, 2.0F, 1.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(0, 34).addBox(-2.0F, -2.5312F, -2.6297F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-2.0F, -2.5312F, 8.3703F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 55).addBox(-1.0F, -1.5312F, 7.8703F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.9688F, 2.6297F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 19).addBox(-1.0F, -1.0F, -12.0F, 2.0F, 1.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.9312F, 7.5703F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(49, 33).addBox(-2.5F, -1.5F, -0.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, 2.4688F, -13.1297F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, 0.0F, -0.9142F, 2.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.9546F, 1.0917F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(50, 13).addBox(-1.0F, 0.0F, -2.9142F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.1546F, 0.2917F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(20, 48).addBox(-1.0F, -3.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.9688F, -2.6297F, 0.3491F, 0.0F, 0.0F));

        PartDefinition muzzle = partdefinition.addOrReplaceChild("muzzle", CubeListBuilder.create(), PartPose.offset(0.0F, -10.0F, -11.0F));

        PartDefinition cube_r6 = muzzle.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(38, 13).addBox(-2.5F, -2.0F, -3.5F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.5F, -1.0F, -5.0F, 3.0F, 3.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }
//    @Override
//    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        this.muzzle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}