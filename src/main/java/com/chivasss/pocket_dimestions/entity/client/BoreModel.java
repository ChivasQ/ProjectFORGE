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
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    //public static final ResourceLocation LAYER_LOCATION = new ResourceLocation(PocketDim.MODID, "bore");
    private final ModelPart main;

    public BoreModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.main = root.getChild("main");
    }


    public static LayerDefinition createLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(5, 5).addBox(-11.0F, -8.0F, 4.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-13.0F, -5.5F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        PartDefinition cube_r1 = main.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -4.0F, -8.75F, 6.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -5.5F, 8.75F, 0.0F, 0.0F, -0.7854F));

        PartDefinition handle = main.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offset(-5.9278F, -6.1464F, 1.3774F));

        PartDefinition cube_r2 = handle.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 5).addBox(1.25F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-5.0F, -1.0F, -1.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0722F, -0.1036F, 0.1226F, 0.0F, 0.3927F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

//    @Override
//    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//
//    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}