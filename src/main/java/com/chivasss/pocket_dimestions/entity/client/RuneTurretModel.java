package com.chivasss.pocket_dimestions.entity.client;


import com.chivasss.pocket_dimestions.entity.animations.ModAnimationDefinitions;
import com.chivasss.pocket_dimestions.entity.custom.RuneTurretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.animation.definitions.WardenAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.joml.Vector3f;

public class RuneTurretModel<T extends Entity> extends HierarchicalModel<T> {
    private final ModelPart tower;
    private final ModelPart head;
    private final ModelPart turret_main;
    public RuneTurretModel(ModelPart root) {
        this.turret_main = root.getChild("turret_main");
        this.head = turret_main.getChild("turret");
        this.tower = turret_main.getChild("tower");
    }






    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition turret_main = partdefinition.addOrReplaceChild("turret_main", CubeListBuilder.create(), PartPose.offset(0.0F, -37.5F, -1.0F));

        PartDefinition tower = turret_main.addOrReplaceChild("tower", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -41.0F, -12.0F, 24.0F, 52.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 61.5F, 1.0F));

        PartDefinition turret = turret_main.addOrReplaceChild("turret", CubeListBuilder.create().texOffs(70, 50).addBox(-3.0F, -7.5F, -17.0F, 6.0F, 13.0F, 26.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-1.0F, 0.0F, -18.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }



    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
        this.animate(((RuneTurretEntity)entity).departureAnimationState, ModAnimationDefinitions.RUNE_TURRET_DEPARTURE, ageInTicks, 1f);

    }



    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
//        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
//        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
//        this.tower.setRotation(0,0,0);
//        this.turret_main.setRotation(0,0,0);
        this.head.yRot = pNetHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = pHeadPitch * ((float)Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        turret_main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public ModelPart root() {
        return turret_main;
    }
}