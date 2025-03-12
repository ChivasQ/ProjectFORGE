package com.chivasss.pocket_dimestions.entity.client.sandworm;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;

public class SandwormHeadModel<T extends Entity> extends EntityModel<T> {
    private final ModelPart part1;
    private final ModelPart blade0;
    private final ModelPart blade1;
    private final ModelPart blade2;
    private final ModelPart blade3;
    private final ModelPart DRILL;
    private final ModelPart DUR_DUR_DUR;

    public SandwormHeadModel(ModelPart root) {
        this.part1 = root.getChild("part1");
        this.blade0 = this.part1.getChild("blade0");
        this.blade1 = this.part1.getChild("blade1");
        this.blade2 = this.part1.getChild("blade2");
        this.blade3 = this.part1.getChild("blade3");
        this.DRILL = root.getChild("DRILL");
        this.DUR_DUR_DUR = this.DRILL.getChild("DUR_DUR_DUR");
    }


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition part1 = partdefinition.addOrReplaceChild("part1", CubeListBuilder.create().texOffs(0, 96).addBox(-7.875F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-8.325F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.125F, 24.0F, 0.25F, 1.5708F, 0.0F, 0.0F));

        PartDefinition blade0 = part1.addOrReplaceChild("blade0", CubeListBuilder.create(), PartPose.offset(0.125F, 0.0F, 0.0F));

        PartDefinition banana_r1 = blade0.addOrReplaceChild("banana_r1", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r2 = blade0.addOrReplaceChild("banana_r2", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r3 = blade0.addOrReplaceChild("banana_r3", CubeListBuilder.create().texOffs(0, 52).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(51, 4).mirror().addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade1 = part1.addOrReplaceChild("blade1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition banana_r4 = blade1.addOrReplaceChild("banana_r4", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r5 = blade1.addOrReplaceChild("banana_r5", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r6 = blade1.addOrReplaceChild("banana_r6", CubeListBuilder.create().texOffs(0, 52).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(51, 4).mirror().addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade2 = part1.addOrReplaceChild("blade2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition banana_r7 = blade2.addOrReplaceChild("banana_r7", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r8 = blade2.addOrReplaceChild("banana_r8", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r9 = blade2.addOrReplaceChild("banana_r9", CubeListBuilder.create().texOffs(0, 52).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(51, 4).mirror().addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition blade3 = part1.addOrReplaceChild("blade3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.125F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition banana_r10 = blade3.addOrReplaceChild("banana_r10", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r11 = blade3.addOrReplaceChild("banana_r11", CubeListBuilder.create().texOffs(64, 116).addBox(-8.5F, -3.0F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.4193F, -8.0404F, -0.3185F, 0.3927F, 0.0F, 0.0F));

        PartDefinition banana_r12 = blade3.addOrReplaceChild("banana_r12", CubeListBuilder.create().texOffs(0, 52).addBox(-8.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(0, 52).addBox(-11.0F, -7.0F, -19.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .texOffs(51, 4).mirror().addBox(-13.5F, -2.0F, -11.0F, 8.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.4193F, -8.0404F, 6.1815F, -0.2618F, 0.0F, 0.0F));

        PartDefinition DRILL = partdefinition.addOrReplaceChild("DRILL", CubeListBuilder.create().texOffs(90, 88).addBox(-7.0F, -7.0F, -11.7778F, 14.0F, 14.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(86, 107).addBox(-7.5F, -7.5F, -12.2778F, 15.0F, 15.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(80, 85).addBox(-3.0F, -3.0F, -12.7778F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 0.0278F, 1.5708F, 0.0F, 0.0F));

        PartDefinition DR_r1 = DRILL.addOrReplaceChild("DR_r1", CubeListBuilder.create().texOffs(90, 35).addBox(-1.0F, -1.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 18.2222F, 0.0F, 0.0F, -0.5236F));

        PartDefinition DR_r2 = DRILL.addOrReplaceChild("DR_r2", CubeListBuilder.create().texOffs(82, 46).addBox(-2.0F, -2.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 14.2222F, 0.0F, 0.0F, -0.4363F));

        PartDefinition DR_r3 = DRILL.addOrReplaceChild("DR_r3", CubeListBuilder.create().texOffs(76, 63).addBox(-3.0F, -3.0F, -5.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.2222F, 0.0F, 0.0F, -0.3491F));

        PartDefinition DR_r4 = DRILL.addOrReplaceChild("DR_r4", CubeListBuilder.create().texOffs(104, 1).addBox(-4.0F, -4.0F, -5.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.2222F, 0.0F, 0.0F, -0.2618F));

        PartDefinition DR_r5 = DRILL.addOrReplaceChild("DR_r5", CubeListBuilder.create().texOffs(100, 26).addBox(-5.0F, -5.0F, -5.0F, 10.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 2.2222F, 0.0F, 0.0F, -0.1745F));

        PartDefinition DR_r6 = DRILL.addOrReplaceChild("DR_r6", CubeListBuilder.create().texOffs(96, 55).addBox(-6.0F, -6.0F, -1.0F, 12.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -5.7778F, 0.0F, 0.0F, -0.0873F));

        PartDefinition DUR_DUR_DUR = DRILL.addOrReplaceChild("DUR_DUR_DUR", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -34.7778F));

        PartDefinition cube_r1 = DUR_DUR_DUR.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(86, 40).addBox(-1.25F, -1.25F, -5.25F, 2.5F, 2.5F, 3.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 53.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r2 = DUR_DUR_DUR.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(78, 54).addBox(-2.25F, -2.25F, -5.25F, 4.5F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 49.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r3 = DUR_DUR_DUR.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(72, 73).addBox(-3.25F, -3.25F, -5.25F, 6.5F, 6.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 45.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition cube_r4 = DUR_DUR_DUR.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(102, 13).addBox(-4.25F, -4.25F, -5.25F, 8.5F, 8.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 41.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r5 = DUR_DUR_DUR.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(98, 40).addBox(-5.25F, -5.25F, -5.25F, 10.5F, 10.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 37.0F, 0.0F, 0.0F, -0.1745F));

        PartDefinition cube_r6 = DUR_DUR_DUR.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(94, 71).addBox(-6.25F, -6.25F, -1.25F, 12.5F, 12.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 29.0F, 0.0F, 0.0F, -0.0873F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.part1.getAllParts().forEach(ModelPart::resetPose);
        this.DRILL.getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch, ageInTicks);
    }

    private void applyHeadRotation(float pNetHeadYaw, float pHeadPitch, float pAgeInTicks) {
//        pNetHeadYaw = Mth.clamp(pNetHeadYaw, -30.0F, 30.0F);
//        pHeadPitch = Mth.clamp(pHeadPitch, -25.0F, 45.0F);
//        this.tower.setRotation(0,0,0);
//        this.turret_main.setRotation(0,0,0);


        this.part1.yRot = pNetHeadYaw * ((float) Math.PI / 180F);
        this.part1.xRot = pHeadPitch * ((float) Math.PI / 180F);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        part1.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        DRILL.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
