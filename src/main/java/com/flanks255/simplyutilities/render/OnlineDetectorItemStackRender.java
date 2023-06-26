package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SimplyUtilities;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class OnlineDetectorItemStackRender extends BlockEntityWithoutLevelRenderer {
    private static final ResourceLocation OFF_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoroff.png");
    private static final ResourceLocation ON_TEXTURE = new ResourceLocation(SimplyUtilities.MODID, "textures/tile/onlinedetectoron.png");
    private final OnlineDetectorModel model;

    public OnlineDetectorItemStackRender(BlockEntityRenderDispatcher renderDispatcher, EntityModelSet modelSet) {
        super(renderDispatcher, modelSet);

        EntityModelSet EntityModelSetThatIsntNULL = Minecraft.getInstance().getEntityModels();
        model = new OnlineDetectorModel(EntityModelSetThatIsntNULL.bakeLayer(ModelLayers.ONLINEDETECTOR));
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext p_239207_2_, PoseStack matrixStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        VertexConsumer vertexBuilder = buffer.getBuffer(RenderType.entitySolid(OFF_TEXTURE));

        matrixStack.pushPose();
        matrixStack.translate(0.5D, 1.5D, 0.5D);
        matrixStack.scale(-1, -1, 1);


        model.renderToBuffer(matrixStack, vertexBuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1,1,1,1);

        matrixStack.popPose();
    }
}
