package com.chivasss.pocket_dimestions.entity.client;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class RuneTurretModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart main;
    private final ModelPart turrel;
    public RuneTurretModel(ModelPart root) {
        this.main = root.getChild("main");
        this.turrel = main.getChild("turret");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, -37.5F, -1.0F));

        PartDefinition tower = main.addOrReplaceChild("tower", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -41.0F, -12.0F, 24.0F, 52.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 61.5F, 1.0F));

        PartDefinition turret = main.addOrReplaceChild("turret", CubeListBuilder.create().texOffs(70, 50).addBox(-3.0F, -7.5F, -11.0F, 6.0F, 13.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, 0.0F, 14.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public ModelPart root() {
        return main;
    }
}