package com.chivasss.pocket_dimestions.entity.client.rune_turret;


import com.chivasss.pocket_dimestions.entity.animations.ModAnimationDefinitions;
import com.chivasss.pocket_dimestions.entity.custom.rune_turret.RuneTurretEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

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

        PartDefinition turret_main = partdefinition.addOrReplaceChild("turret_main", CubeListBuilder.create(), PartPose.offset(5.8F, 17.0F, 5.0F));

        PartDefinition tower = turret_main.addOrReplaceChild("tower", CubeListBuilder.create(), PartPose.offset(-1.9835F, -37.7962F, -1.9808F));

        PartDefinition cube_r1 = tower.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-9.093F, 0.3048F, -9.0877F, 18.0F, 90.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0165F, -40.2038F, -2.0192F, 0.0523F, 0.0027F, -0.0523F));

        PartDefinition turret = turret_main.addOrReplaceChild("turret", CubeListBuilder.create(), PartPose.offset(-5.0F, -95.0F, -4.0F));

        PartDefinition Core = turret.addOrReplaceChild("Core", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(74, 1).addBox(-3.5F, -3.5F, -3.5F, 7.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, -0.0873F, 0.0873F));

        PartDefinition Runs = Core.addOrReplaceChild("Runs", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r2 = Runs.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r3 = Runs.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r4 = Runs.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r5 = Runs.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = Runs.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, -3.75F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition Cubes = turret.addOrReplaceChild("Cubes", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0699F, -0.0522F, 0.0037F));

        PartDefinition Cubes_1 = Cubes.addOrReplaceChild("Cubes_1", CubeListBuilder.create().texOffs(3, 3).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -11.0F, 0.0F));

        PartDefinition Cubes_2 = Cubes.addOrReplaceChild("Cubes_2", CubeListBuilder.create().texOffs(3, 6).mirror().addBox(-4.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, 0.0F, 0.0F));

        PartDefinition Cubes_3 = Cubes.addOrReplaceChild("Cubes_3", CubeListBuilder.create().texOffs(3, 9).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0F, 0.0F));

        PartDefinition Cubes_4 = Cubes.addOrReplaceChild("Cubes_4", CubeListBuilder.create().texOffs(26, 17).addBox(-2.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 0.0F, 0.0F));

        PartDefinition Kvarc = Cubes.addOrReplaceChild("Kvarc", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1752F, 0.0859F, 0.0152F));

        PartDefinition Kvarc_1 = Kvarc.addOrReplaceChild("Kvarc_1", CubeListBuilder.create().texOffs(5, 5).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, -8.5F, 0.0F));

        PartDefinition Kvarc_2 = Kvarc.addOrReplaceChild("Kvarc_2", CubeListBuilder.create().texOffs(5, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, -8.5F, 0.0F));

        PartDefinition Kvarc_3 = Kvarc.addOrReplaceChild("Kvarc_3", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.5F, 8.5F, 0.0F));

        PartDefinition Kvarc_4 = Kvarc.addOrReplaceChild("Kvarc_4", CubeListBuilder.create().texOffs(5, 5).mirror().addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.5F, 8.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 126, 126);
    }
    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
        this.animate(((RuneTurretEntity)entity).departureAnimationState, ModAnimationDefinitions.TOWER_EMERGING, ageInTicks, 1f);
        this.animate(((RuneTurretEntity)entity).idleAnimationState, ModAnimationDefinitions.TOWER_IDLE, ageInTicks, 1f);

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