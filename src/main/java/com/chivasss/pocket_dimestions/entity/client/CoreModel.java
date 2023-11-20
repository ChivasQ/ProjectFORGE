package com.chivasss.pocket_dimestions.entity.client;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.chivasss.pocket_dimestions.entity.animations.ModAnimationDefinitions;
import com.chivasss.pocket_dimestions.entity.custom.CoreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.fml.common.Mod;

public class CoreModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart main;
	private final ModelPart core;

	public CoreModel(ModelPart root) {
		this.main = root.getChild("main");
		this.core = main.getChild("core");
	}



	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition fics = main.addOrReplaceChild("fics", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition runs2 = fics.addOrReplaceChild("runs2", CubeListBuilder.create(), PartPose.offset(-2.8237F, 2.7962F, 2.8237F));

		PartDefinition cube_r1 = runs2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(21, 12).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, 0.0724F, -0.6109F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = runs2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(12, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, -0.0724F, -0.6109F, -0.7854F, 0.0F));

		PartDefinition runs3 = fics.addOrReplaceChild("runs3", CubeListBuilder.create(), PartPose.offset(2.8237F, 2.7962F, 2.8237F));

		PartDefinition cube_r3 = runs3.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 18).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, 0.0724F, -0.6109F, 0.7854F, 0.0F));

		PartDefinition cube_r4 = runs3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, -0.0724F, -0.6109F, 0.7854F, 0.0F));

		PartDefinition runs4 = fics.addOrReplaceChild("runs4", CubeListBuilder.create(), PartPose.offset(2.8237F, 2.7962F, -2.8237F));

		PartDefinition cube_r5 = runs4.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(20, 15).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, -0.0724F, -0.6109F, 2.3562F, 0.0F));

		PartDefinition cube_r6 = runs4.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(4, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, 0.0724F, -0.6109F, 2.3562F, 0.0F));

		PartDefinition runs5 = fics.addOrReplaceChild("runs5", CubeListBuilder.create(), PartPose.offset(-2.8237F, 2.7962F, -2.8237F));

		PartDefinition cube_r7 = runs5.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(18, 3).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, -0.0724F, -0.6109F, -2.3562F, 0.0F));

		PartDefinition cube_r8 = runs5.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, 0.0724F, -0.6109F, -2.3562F, 0.0F));

		PartDefinition runs6 = fics.addOrReplaceChild("runs6", CubeListBuilder.create(), PartPose.offset(-2.8237F, -2.7962F, -2.8237F));

		PartDefinition cube_r9 = runs6.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(18, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, -0.0724F, 0.6109F, -0.7854F, 3.1416F));

		PartDefinition cube_r10 = runs6.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(20, 21).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, 0.0724F, 0.6109F, -0.7854F, 3.1416F));

		PartDefinition runs7 = fics.addOrReplaceChild("runs7", CubeListBuilder.create(), PartPose.offset(2.8237F, -2.7962F, -2.8237F));

		PartDefinition cube_r11 = runs7.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(15, 12).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, -0.0724F, 0.6109F, 0.7854F, 3.1416F));

		PartDefinition cube_r12 = runs7.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(15, 15).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, 0.0724F, 0.6109F, 0.7854F, 3.1416F));

		PartDefinition runs8 = fics.addOrReplaceChild("runs8", CubeListBuilder.create(), PartPose.offset(-2.8237F, -2.7962F, 2.8237F));

		PartDefinition cube_r13 = runs8.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 3).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, 0.0724F, -2.5307F, -0.7854F, 0.0F));

		PartDefinition cube_r14 = runs8.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 14).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, -0.0724F, -2.5307F, -0.7854F, 0.0F));

		PartDefinition runs9 = fics.addOrReplaceChild("runs9", CubeListBuilder.create(), PartPose.offset(2.8237F, -2.7962F, 2.8237F));

		PartDefinition cube_r15 = runs9.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, 0.0724F, -2.5307F, 0.7854F, 0.0F));

		PartDefinition cube_r16 = runs9.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, -0.0724F, -2.5307F, 0.7854F, 0.0F));

		PartDefinition cubes = main.addOrReplaceChild("cubes", CubeListBuilder.create(), PartPose.offset(0.0F, -8.0F, 0.0F));

		PartDefinition cube1 = cubes.addOrReplaceChild("cube1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube2 = cubes.addOrReplaceChild("cube2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube3 = cubes.addOrReplaceChild("cube3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube4 = cubes.addOrReplaceChild("cube4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube5 = cubes.addOrReplaceChild("cube5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube6 = cubes.addOrReplaceChild("cube6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube7 = cubes.addOrReplaceChild("cube7", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube8 = cubes.addOrReplaceChild("cube8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition core = main.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -2.475F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.9964F, -0.0036F, -0.6109F, 0.6109F, 0.6109F));

		PartDefinition runs1 = core.addOrReplaceChild("runs1", CubeListBuilder.create().texOffs(4, 24).addBox(-0.375F, -0.1464F, -3.0786F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(-0.375F, -0.1464F, 3.125F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.625F, -0.8536F, -0.0214F));

		PartDefinition cube_r17 = runs1.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(24, 0).addBox(-0.8536F, -0.1464F, 2.6F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r18 = runs1.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(24, 2).addBox(-1.1464F, -0.1464F, 3.6F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r19 = runs1.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(20, 23).addBox(-0.5F, -0.8536F, -3.9536F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r20 = runs1.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(16, 22).addBox(-0.5F, -1.1464F, -2.2464F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		//this.animateWalk(ModAnimationDefinitions.CORE_IDLE, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.animate(((CoreEntity) entity).idleAnimationState, ModAnimationDefinitions.CORE_IDLE, ageInTicks, 1f);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		main.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return main;
	}
}