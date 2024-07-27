package com.flanks255.simplyutilities;

import com.flanks255.simplyutilities.configuration.ConfigCache;
import com.flanks255.simplyutilities.render.OnlineDetectorItemStackRender;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.ViewportEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import javax.annotation.Nonnull;

public class SUClientEvents {
    public static void onClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Nonnull
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new OnlineDetectorItemStackRender(null, null);
            }
        }, SUBlocks.ONLINE_DETECTOR.getItem());
    }

    public static void onViewportEvent(ViewportEvent.ComputeFov event) {
        if (SimplyUtilities.keyBinds.get(0).isDown()) {
            event.setFOV(ConfigCache.zoom_fov);
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().options.smoothCamera = true;
        }
        else {
            if (ConfigCache.zoom_smooth)
                Minecraft.getInstance().options.smoothCamera = false;
        }
    }

    public static void registerKeyBinding(final RegisterKeyMappingsEvent event) {
        SimplyUtilities.keyBinds.addFirst(new KeyMapping("key.simplyutilities.zoom.desc", -1, "key.simplyutilities.category"));
        event.register(SimplyUtilities.keyBinds.getFirst());
    }
}
