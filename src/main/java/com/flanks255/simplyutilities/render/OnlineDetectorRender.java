package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.tile.TEOnlineDetector;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

public class OnlineDetectorRender extends TileEntityRenderer<TEOnlineDetector> {
    public OnlineDetectorRender(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    private static final ResourceLocation OFF_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoroff.png");
    private static final ResourceLocation ON_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoron.png");
    private final OnlineDetectorModel model = new OnlineDetectorModel();

    @Override
    public void render(TEOnlineDetector tileEntityIn, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn == null || !tileEntityIn.hasWorld())
            return;

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntitySolid(OFF_TEXTURE));

        matrixStack.push();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(-1, -1, 1);


        float angle1 = tileEntityIn.ringAngle + (tileEntityIn.ringAngle - tileEntityIn.prevRingAngle) * partialTicks;
        float angle2 = -angle1;

        float eyeInterpolated = tileEntityIn.prevEyeAngle + (tileEntityIn.eyeAngle - tileEntityIn.prevEyeAngle) * partialTicks;

        model.ArmAndStand.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        model.Base.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        model.bone.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);

        matrixStack.push();
        matrixStack.translate(-0.05,0,-0.1);
        matrixStack.rotate(Vector3f.XN.rotationDegrees(-7.5f));
        matrixStack.rotate(Vector3f.YN.rotationDegrees(angle1));
        model.Ring1.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(0.05,0,0.1);
        matrixStack.rotate(Vector3f.XN.rotationDegrees(7.5f));
        matrixStack.rotate(Vector3f.YN.rotationDegrees(angle2));
        model.Ring2.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(eyeInterpolated));
        model.Eye.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();



        matrixStack.pop();
    }
}
