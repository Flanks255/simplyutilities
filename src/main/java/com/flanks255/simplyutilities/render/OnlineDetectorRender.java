package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.flanks255.simplyutilities.tile.BEOnlineDetector;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class OnlineDetectorRender implements BlockEntityRenderer<BEOnlineDetector> {
    public OnlineDetectorRender(BlockEntityRendererProvider.Context blah) {
        model = new OnlineDetectorModel(blah.bakeLayer(ModelLayers.ONLINEDETECTOR));
    }

    private static final ResourceLocation OFF_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoroff.png");
    private static final ResourceLocation ON_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoron.png");
    private final OnlineDetectorModel model;

    @Override
    public void render(BEOnlineDetector tileEntityIn, float partialTicks, @Nonnull PoseStack matrixStack, @Nonnull MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (tileEntityIn == null || !tileEntityIn.hasLevel())
            return;

        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entitySolid(tileEntityIn.onlineState?ON_TEXTURE:OFF_TEXTURE));

        matrixStack.pushPose();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(-1, -1, 1);

        switch (tileEntityIn.getBlockState().getValue(BlockStateProperties.FACING)) {
            case NORTH -> {
                matrixStack.translate(0, 1, -1);
                matrixStack.mulPose(Axis.XP.rotationDegrees(90));
            }
            case WEST -> {
                matrixStack.translate(1, 1, 0);
                matrixStack.mulPose(Axis.ZP.rotationDegrees(90));
            }
            case EAST -> {
                matrixStack.translate(-1, 1, 0);
                matrixStack.mulPose(Axis.ZP.rotationDegrees(270));
            }
            case SOUTH -> {
                matrixStack.translate(0, 1, 1);
                matrixStack.mulPose(Axis.XP.rotationDegrees(270));
            }
            case DOWN -> {
                matrixStack.translate(0, 2, 0);
                matrixStack.mulPose(Axis.XP.rotationDegrees(180));
            }
            default -> {
            }
        }


        float angle1 = tileEntityIn.ringAngle + (tileEntityIn.ringAngle - tileEntityIn.prevRingAngle) * partialTicks;
        float angle2 = -angle1;

        float eyeInterpolated = tileEntityIn.baseEyeAngle + Mth.lerp(partialTicks, tileEntityIn.prevEyeOffset, tileEntityIn.currentEyeOffset);

        model.ArmAndStand.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        model.Base.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        model.bone.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);

        matrixStack.pushPose();
        matrixStack.translate(-0.05,0,-0.1);
        matrixStack.mulPose(Axis.XN.rotationDegrees(-7.5f));
        matrixStack.mulPose(Axis.YN.rotationDegrees(angle1));
        model.Ring1.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();

        matrixStack.pushPose();
        matrixStack.translate(0.05,0,0.1);
        matrixStack.mulPose(Axis.XN.rotationDegrees(7.5f));
        matrixStack.mulPose(Axis.YN.rotationDegrees(angle2));
        model.Ring2.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();

        matrixStack.pushPose();
        if (tileEntityIn.onlineState)
            matrixStack.mulPose(Axis.YP.rotationDegrees(eyeInterpolated));
        model.Eye.render(matrixStack, vertexBuilder, combinedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStack.popPose();

        matrixStack.popPose();
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
