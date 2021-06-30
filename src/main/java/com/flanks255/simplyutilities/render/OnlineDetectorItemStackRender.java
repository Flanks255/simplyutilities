package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class OnlineDetectorItemStackRender extends ItemStackTileEntityRenderer {
    private static final ResourceLocation OFF_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoroff.png");
    private static final ResourceLocation ON_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoron.png");
    private final OnlineDetectorModel model = new OnlineDetectorModel();

    @Override
    public void func_239207_a_(ItemStack stack, ItemCameraTransforms.TransformType p_239207_2_, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        IVertexBuilder vertexBuilder = buffer.getBuffer(RenderType.getEntitySolid(OFF_TEXTURE));

        matrixStack.push();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(-1, -1, 1);


        model.render(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1,1,1,1);

        matrixStack.pop();
    }
}
