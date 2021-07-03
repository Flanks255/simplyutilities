package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.tile.TEOnlineDetector;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
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

        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntitySolid(tileEntityIn.onlineState?ON_TEXTURE:OFF_TEXTURE));

        matrixStack.push();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(-1, -1, 1);

        switch(tileEntityIn.getBlockState().get(BlockStateProperties.FACING)) {
            case NORTH:
                matrixStack.translate(0, 1, -1);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
                break;
            case WEST:
                matrixStack.translate(1, 1, 0);
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(90));
                break;
            case EAST:
                matrixStack.translate(-1, 1, 0);
                matrixStack.rotate(Vector3f.ZP.rotationDegrees(270));
                break;
            case SOUTH:
                matrixStack.translate(0, 1, 1);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(270));
                break;
            case DOWN:
                matrixStack.translate(0, 2, 0);
                matrixStack.rotate(Vector3f.XP.rotationDegrees(180));
                break;
            default:
                break;
        }


        float angle1 = tileEntityIn.ringAngle + (tileEntityIn.ringAngle - tileEntityIn.prevRingAngle) * partialTicks;
        float angle2 = -angle1;

        float eyeInterpolated = tileEntityIn.baseEyeAngle + MathHelper.lerp(partialTicks, tileEntityIn.prevEyeOffset, tileEntityIn.currentEyeOffset);

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
        if (tileEntityIn.onlineState)
            matrixStack.rotate(Vector3f.YP.rotationDegrees(eyeInterpolated));
        model.Eye.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();

        matrixStack.pop();
/*
        matrixStack.push();
        matrixStack.translate(1,1.5,0);
        matrixStack.scale(-1, -1, 1);
        matrixStack.scale(0.0075F, 0.0075F, 0.0075F);

        FontRenderer font = Minecraft.getInstance().fontRenderer;
        font.drawString(matrixStack, "eyeInterpolated: " + eyeInterpolated, 0,-10, 0xffffff);
        font.drawString(matrixStack, "baseEyeAngle: " + tileEntityIn.baseEyeAngle, 0,0, 0xffffff);
        font.drawString(matrixStack, "eyeOffsetTarget: " + tileEntityIn.eyeOffsetTarget, 0,10, 0xffffff);
        font.drawString(matrixStack, "sumTarget: " + (tileEntityIn.baseEyeAngle + tileEntityIn.eyeOffsetTarget), 0,20, 0xffffff);
        font.drawString(matrixStack, "currentOffset: " + tileEntityIn.currentEyeOffset, 0,30, 0xffffff);
        font.drawString(matrixStack, "prevOffset: " + tileEntityIn.prevEyeOffset, 0,40, 0xffffff);

        matrixStack.pop();*/
    }
}
