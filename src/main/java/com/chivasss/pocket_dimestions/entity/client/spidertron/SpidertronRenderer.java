package com.chivasss.pocket_dimestions.entity.client.spidertron;

import com.chivasss.pocket_dimestions.PocketDim;
import com.chivasss.pocket_dimestions.entity.custom.spidertron.Spidertron;
import com.chivasss.pocket_dimestions.util.RendererUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class SpidertronRenderer<T extends Spidertron> extends EntityRenderer<T> {
    public static final ResourceLocation LAYER_LOCATION = new ResourceLocation(PocketDim.MODID, "textures/entity/spidertron_base.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(LAYER_LOCATION);
    private static final RenderType LINE_RENDER_TYPE = RenderType.debugQuads();
    public static ModelPart base;
    public static ModelPart leg1;
    public static ModelPart sub_leg1;
    protected final SpidertronModel<T> model;
    private static final double LEG_LEN = 4;
    private static final double SUB_LEG_LEN = 3.375;
    public SpidertronRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation pLayer) {
        super(pContext);
        this.model = new SpidertronModel<>(pContext.bakeLayer(pLayer));
        base = this.model.root().getChild("base");
        leg1 = this.model.root().getChild("bone1").getChild("leg1");
        sub_leg1 = this.model.root().getChild("bone1").getChild("leg1").getChild("sub_leg1");

    }

    private static void vertex(VertexConsumer pConsumer, Matrix4f matrix4f, Matrix3f p_253881_, double pX, double pY, double pZ, int pRed, int pGreen, int pBlue, float pU, float pV) {
        pConsumer.vertex(matrix4f, (float) pX, (float) pY, (float) pZ).color(pRed, pGreen, pBlue, 255).uv(pU, pV).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal(p_253881_, 0.0F, 1.0F, 0.0F).endVertex();
    }

    @Override
    public boolean shouldRender(T pLivingEntity, Frustum pCamera, double pCamX, double pCamY, double pCamZ) {
        return true;
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTick, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
//        for (int i = 0; i < pEntity.getAMOUNT_OF_LEGS(); i++) {
//            Vec3 vec = pEntity.getRotatedPoint(pEntity.position(), (double) (i * 360) / pEntity.getAMOUNT_OF_LEGS() + pEntityYaw, pEntity.position().add(3, 0, 0));
//            pMatrixStack.pushPose();
//            pMatrixStack.translate(0, 0.1F, 0);
//            Vec3 vec3 = pEntity.getHeight1(vec.add(0, 10, 0), vec.subtract(0, 10, 0), 0).getLocation();
//            Vec3 vec31 = pEntity.position();
//
//            RendererUtils.DrawLine(vec3, vec31, pMatrixStack, pBuffer);
//
//            pMatrixStack.popPose();
//
//        }

        Vec3 translate = pEntity.getRotatedPoint(Vec3.ZERO, 180, Vec3.ZERO.add(1, 0, 0));
        Vec3 vec = pEntity.getRotatedPoint(pEntity.getPosition(pPartialTick), 180, pEntity.getPosition(pPartialTick).add(5, 0, 0));
        Vec3 start = pEntity.getPosition(pPartialTick).add(translate);
        Vec3 end = pEntity.getHeight(vec.add(0, 10, 0), vec.add(0, -10, 0), 0).getLocation();

        RendererUtils.DrawLine(end, start, translate ,pMatrixStack, pBuffer);
        double[] points = find_intersection_points(start.x, start.y, LEG_LEN*1.2, end.x, end.y, SUB_LEG_LEN);

        if (points.length > 0) {
            System.out.println(points[2] + " " + points[3]);
            Vec3 p3 = new Vec3(points[2], points[3], start.z);
            RendererUtils.DrawLine(start, p3, end.subtract(pEntity.getPosition(pPartialTick)), pMatrixStack, pBuffer);
            RendererUtils.DrawLine(end, p3, translate, pMatrixStack, pBuffer);


        }


//        pMatrixStack.pushPose();
//        pMatrixStack.scale(-1,-1,-1);
//        pMatrixStack.translate(0,-1.5,0);
//        //this.model.renderToBuffer(pMatrixStack, pBuffer.getBuffer(RENDER_TYPE), pPackedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
//        pMatrixStack.popPose();





    }

    private static float GetZPAngle(Vec3 player, Vec3 shield) {
        double d0 = Math.atan((shield.y-player.y)/ player.distanceTo(new Vec3(shield.x, player.y, shield.z)));
        return Math.round(Math.toDegrees(d0)-90);
    }
    private static float GetYPAngle(Vec3 player, Vec3 shield) {
        double tg = Math.atan((shield.z - player.z)/(shield.x - player.x));
        long deg = Math.round(Math.toDegrees(tg));
        if (player.x > shield.x && player.z > shield.z){
            deg = deg+180;
        } else if(player.x > shield.x && player.z < shield.z){
            deg = deg + 180;
        }
        return deg;
    }
    public Vec2 CalcInvKinPoint(Vec2 endEffectorMove, Vec2 base, double linkLength0, double linkLength1) {
        Vec2 relativeEndEffector = new Vec2(endEffectorMove.x - base.x, endEffectorMove.y - base.y);
        double distance = Math.max(Math.abs(linkLength0 - linkLength1), Math.min(linkLength0 + linkLength1, Math.sqrt(relativeEndEffector.x * relativeEndEffector.x + relativeEndEffector.y * relativeEndEffector.y)));
        double endEffectorAngle = Math.atan2(relativeEndEffector.y, relativeEndEffector.x);
        Vec2 scaledEndEffector = new Vec2((float) (Math.cos(endEffectorAngle) * distance), (float) (Math.sin(endEffectorAngle) * distance));
        double magnitudeSquared = scaledEndEffector.x * scaledEndEffector.x + scaledEndEffector.y * scaledEndEffector.y;
        double elbowAngle = Math.acos((linkLength0 * linkLength0 - linkLength1 * linkLength1 + magnitudeSquared) / (2 * linkLength0 * Math.sqrt(magnitudeSquared)));
        return new Vec2((float) (Math.cos(elbowAngle) * linkLength0), (float) (Math.sin(elbowAngle) * linkLength0));
    }

    public double[] find_intersection_points(double x0, double y0, double r0, double x1, double y1, double r1) {
        double d = Math.sqrt(Mth.square((x0 - x1) + Mth.square(y0 - y1)));
        if (d > (r0 + r1) || d < Math.abs(r0 - r1)){
            return new double[]{};
        }

        double a = (Mth.square(r0) - Mth.square(r1) + Mth.square(d))/(2 * d);
        double h = Math.sqrt(Mth.square(r0) - Mth.square(a));

        double x2 = x0 + a * (x1 - x0) / d;
        double y2 = y0 + a * (y1 - y0) / d;
        double x3 = x2 - h * (y1 - y0) / d;
        double y3 = y2 + h * (x1 - x0) / d;

        return new double[]{x2, y2, x3, y3};
    }


    public double CalcTriangleAngle(double a, double b, double c){
        return Math.acos((Mth.square(b) + Mth.square(c) - Mth.square(a))/(2*b*c));
    }

    @Override
    public ResourceLocation getTextureLocation(T pEntity) {
        return LAYER_LOCATION;
    }

}

