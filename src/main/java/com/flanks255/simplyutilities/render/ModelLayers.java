package com.flanks255.simplyutilities.render;

import com.flanks255.simplyutilities.SUBlocks;
import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class ModelLayers {
    public static final ModelLayerLocation ONLINEDETECTOR = new ModelLayerLocation(new ResourceLocation(SimplyUtilities.MODID, "onlinedetector"), "onlinedetector");

    public static void init(IEventBus bus) {
        bus.addListener(ModelLayers::registerEntityRenderers);
        bus.addListener(ModelLayers::registerLayerDefinitions);
    }
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(SUBlocks.ONLINE_DETECTOR.getBlockEntityType(), OnlineDetectorRender::new);
    }
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ONLINEDETECTOR, OnlineDetectorModel::createBodyLayer);
    }
}
