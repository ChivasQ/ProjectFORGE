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

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition core = main.addOrReplaceChild("core", CubeListBuilder.create().texOffs(136, 136).addBox(-2.5F, -2.475F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
				.texOffs(122, 128).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0036F, -0.0036F, -0.6109F, 0.6109F, 0.6109F));

		PartDefinition runscore = core.addOrReplaceChild("runscore", CubeListBuilder.create().texOffs(0, 0).addBox(-0.625F, -0.3964F, -3.0786F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.625F, -0.3964F, 3.125F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.625F, -0.8536F, -0.0214F));

		PartDefinition cube_r1 = runscore.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.1036F, -0.3964F, 2.6F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r2 = runscore.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.3964F, -0.3964F, 3.6F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r3 = runscore.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -1.1036F, -3.9536F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, 1.5708F, 0.0F, 0.0F));

		PartDefinition cube_r4 = runscore.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).addBox(-0.75F, -1.3964F, -2.2464F, 2.5F, 2.5F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 0.0F, -0.125F, -1.5708F, 0.0F, 0.0F));

		PartDefinition cubes = main.addOrReplaceChild("cubes", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Z_cube1 = cubes.addOrReplaceChild("Z_cube1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fregment_1 = Z_cube1.addOrReplaceChild("fregment_1", CubeListBuilder.create().texOffs(64, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, 7.0F, 7.0F));

		PartDefinition fregment_2 = Z_cube1.addOrReplaceChild("fregment_2", CubeListBuilder.create().texOffs(80, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.5F, 6.75F, 15.5F));

		PartDefinition fregment_3 = Z_cube1.addOrReplaceChild("fregment_3", CubeListBuilder.create().texOffs(80, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.75F, 7.0F, 6.5F));

		PartDefinition fregment_4 = Z_cube1.addOrReplaceChild("fregment_4", CubeListBuilder.create().texOffs(80, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.25F, 7.0F, 15.5F));

		PartDefinition fregment_5 = Z_cube1.addOrReplaceChild("fregment_5", CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.75F, 15.5F, 6.0F));

		PartDefinition fregment_6 = Z_cube1.addOrReplaceChild("fregment_6", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.75F, 15.25F, 15.25F));

		PartDefinition fregment_7 = Z_cube1.addOrReplaceChild("fregment_7", CubeListBuilder.create().texOffs(48, 56).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.25F, 15.5F, 6.5F));

		PartDefinition fregment_8 = Z_cube1.addOrReplaceChild("fregment_8", CubeListBuilder.create().texOffs(72, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.25F, 15.75F, 15.5F));

		PartDefinition Z_cube3 = cubes.addOrReplaceChild("Z_cube3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fragment_1 = Z_cube3.addOrReplaceChild("fragment_1", CubeListBuilder.create().texOffs(96, 96).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, 7.0F, -7.0F));

		PartDefinition fragment_2 = Z_cube3.addOrReplaceChild("fragment_2", CubeListBuilder.create().texOffs(96, 96).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, 15.5F, -6.75F));

		PartDefinition fragment_3 = Z_cube3.addOrReplaceChild("fragment_3", CubeListBuilder.create().texOffs(96, 96).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.5F, 15.5F, -6.5F));

		PartDefinition fragment_4 = Z_cube3.addOrReplaceChild("fragment_4", CubeListBuilder.create().texOffs(96, 96).addBox(11.25F, 11.25F, -19.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition fragment_5 = Z_cube3.addOrReplaceChild("fragment_5", CubeListBuilder.create().texOffs(0, 8).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, 15.25F, -15.5F));

		PartDefinition fragment_6 = Z_cube3.addOrReplaceChild("fragment_6", CubeListBuilder.create().texOffs(24, 32).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, 6.75F, -6.5F));

		PartDefinition fragment_7 = Z_cube3.addOrReplaceChild("fragment_7", CubeListBuilder.create().texOffs(48, 56).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.5F, 7.0F, -15.25F));

		PartDefinition fragment_8 = Z_cube3.addOrReplaceChild("fragment_8", CubeListBuilder.create().texOffs(72, 80).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, 6.75F, -15.25F));

		PartDefinition Z_cube5 = cubes.addOrReplaceChild("Z_cube5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition frogment_1 = Z_cube5.addOrReplaceChild("frogment_1", CubeListBuilder.create().texOffs(96, 112).addBox(-11.0F, -11.0F, -11.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition frogment_2 = Z_cube5.addOrReplaceChild("frogment_2", CubeListBuilder.create().texOffs(112, 112).addBox(-3.3F, -4.0F, -3.9F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-16.2F, -6.75F, -6.6F));

		PartDefinition frogment_3 = Z_cube5.addOrReplaceChild("frogment_3", CubeListBuilder.create().texOffs(112, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.0F, -6.5F, -15.5F));

		PartDefinition frogment_4 = Z_cube5.addOrReplaceChild("frogment_4", CubeListBuilder.create().texOffs(112, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.25F, -6.75F, -15.25F));

		PartDefinition frogment_5 = Z_cube5.addOrReplaceChild("frogment_5", CubeListBuilder.create().texOffs(112, 112).addBox(-4.4F, -4.0F, -4.4F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.35F, -15.5F, -6.35F));

		PartDefinition frogment_6 = Z_cube5.addOrReplaceChild("frogment_6", CubeListBuilder.create().texOffs(8, 0).mirror().addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-15.25F, -15.25F, -6.5F));

		PartDefinition frogment_7 = Z_cube5.addOrReplaceChild("frogment_7", CubeListBuilder.create().texOffs(16, 24).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.75F, -15.0F, -15.25F));

		PartDefinition frogment_8 = Z_cube5.addOrReplaceChild("frogment_8", CubeListBuilder.create().texOffs(40, 48).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-15.5F, -15.0F, -15.5F));

		PartDefinition Z_cube8 = cubes.addOrReplaceChild("Z_cube8", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition frigment_1 = Z_cube8.addOrReplaceChild("frigment_1", CubeListBuilder.create().texOffs(64, 72).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.0F, -7.0F, 7.0F));

		PartDefinition frigment_2 = Z_cube8.addOrReplaceChild("frigment_2", CubeListBuilder.create().texOffs(88, 96).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.25F, -6.5F, 6.5F));

		PartDefinition frigment_3 = Z_cube8.addOrReplaceChild("frigment_3", CubeListBuilder.create().texOffs(96, 64).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, -6.75F, 15.75F));

		PartDefinition frigment_4 = Z_cube8.addOrReplaceChild("frigment_4", CubeListBuilder.create().texOffs(32, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.75F, -6.75F, 15.5F));

		PartDefinition frigment_5 = Z_cube8.addOrReplaceChild("frigment_5", CubeListBuilder.create().texOffs(48, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, -15.5F, 6.5F));

		PartDefinition frigment_6 = Z_cube8.addOrReplaceChild("frigment_6", CubeListBuilder.create().texOffs(96, 88).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(15.75F, -15.0F, 6.25F));

		PartDefinition frigment_7 = Z_cube8.addOrReplaceChild("frigment_7", CubeListBuilder.create().texOffs(96, 72).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(6.75F, -15.25F, 15.5F));

		PartDefinition frigment_8 = Z_cube8.addOrReplaceChild("frigment_8", CubeListBuilder.create().texOffs(96, 112).addBox(11.5F, -19.0F, 11.75F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube2 = cubes.addOrReplaceChild("cube2", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 11.0F, 11.0F));

		PartDefinition cube4 = cubes.addOrReplaceChild("cube4", CubeListBuilder.create().texOffs(16, 0).mirror().addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-11.0F, 11.0F, -11.0F));

		PartDefinition cube6 = cubes.addOrReplaceChild("cube6", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, -11.0F, -11.0F));

		PartDefinition cube7 = cubes.addOrReplaceChild("cube7", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.0F, -11.0F, 11.0F));

		PartDefinition fics = main.addOrReplaceChild("fics", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition runs1 = fics.addOrReplaceChild("runs1", CubeListBuilder.create(), PartPose.offset(-2.8237F, 2.7962F, 2.8237F));

		PartDefinition cube_r5 = runs1.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, 0.0724F, -0.6109F, -0.7854F, 0.0F));

		PartDefinition cube_r6 = runs1.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, -0.0724F, -0.6109F, -0.7854F, 0.0F));

		PartDefinition runs2 = fics.addOrReplaceChild("runs2", CubeListBuilder.create(), PartPose.offset(2.8237F, 2.7962F, 2.8237F));

		PartDefinition cube_r7 = runs2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, 0.0724F, -0.6109F, 0.7854F, 0.0F));

		PartDefinition cube_r8 = runs2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, -0.0724F, -0.6109F, 0.7854F, 0.0F));

		PartDefinition runs3 = fics.addOrReplaceChild("runs3", CubeListBuilder.create(), PartPose.offset(2.8237F, 2.7962F, -2.8237F));

		PartDefinition cube_r9 = runs3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, -0.0724F, -0.6109F, 2.3562F, 0.0F));

		PartDefinition cube_r10 = runs3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, 0.0724F, -0.6109F, 2.3562F, 0.0F));

		PartDefinition runs4 = fics.addOrReplaceChild("runs4", CubeListBuilder.create(), PartPose.offset(-2.8237F, 2.7962F, -2.8237F));

		PartDefinition cube_r11 = runs4.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, -0.0724F, -0.6109F, -2.3562F, 0.0F));

		PartDefinition cube_r12 = runs4.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, 0.0724F, -0.6109F, -2.3562F, 0.0F));

		PartDefinition runs5 = fics.addOrReplaceChild("runs5", CubeListBuilder.create(), PartPose.offset(-2.8237F, -2.7962F, -2.8237F));

		PartDefinition cube_r13 = runs5.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, -0.0724F, 0.6109F, -0.7854F, 3.1416F));

		PartDefinition cube_r14 = runs5.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, 0.0724F, 0.6109F, -0.7854F, 3.1416F));

		PartDefinition runs6 = fics.addOrReplaceChild("runs6", CubeListBuilder.create(), PartPose.offset(2.8237F, -2.7962F, -2.8237F));

		PartDefinition cube_r15 = runs6.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, -0.0724F, 0.6109F, 0.7854F, 3.1416F));

		PartDefinition cube_r16 = runs6.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, 0.0724F, 0.6109F, 0.7854F, 3.1416F));

		PartDefinition runs7 = fics.addOrReplaceChild("runs7", CubeListBuilder.create(), PartPose.offset(-2.8237F, -2.7962F, 2.8237F));

		PartDefinition cube_r17 = runs7.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, -0.0717F, 0.0724F, -2.5307F, -0.7854F, 0.0F));

		PartDefinition cube_r18 = runs7.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, 0.0717F, -0.0724F, -2.5307F, -0.7854F, 0.0F));

		PartDefinition runs8 = fics.addOrReplaceChild("runs8", CubeListBuilder.create(), PartPose.offset(2.8237F, -2.7962F, 2.8237F));

		PartDefinition cube_r19 = runs8.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0724F, -0.0717F, 0.0724F, -2.5307F, 0.7854F, 0.0F));

		PartDefinition cube_r20 = runs8.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0724F, 0.0717F, -0.0724F, -2.5307F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
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