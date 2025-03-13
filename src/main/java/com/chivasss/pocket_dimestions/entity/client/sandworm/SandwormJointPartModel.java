package com.chivasss.pocket_dimestions.entity.client.sandworm;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SandwormJointPartModel<T extends Entity> extends EntityModel<T> {
    public final ModelPart joint;

    public SandwormJointPartModel(ModelPart root) {
        this.joint = root.getChild("joint");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition joint = partdefinition.addOrReplaceChild("joint", CubeListBuilder.create().texOffs(60, 99).addBox(-5.0F, -5.0F, -2.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 83).addBox(-5.5F, -5.5F, -2.5F, 11.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(88, 99).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }


    @Override
    public void setupAnim(T pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        joint.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
