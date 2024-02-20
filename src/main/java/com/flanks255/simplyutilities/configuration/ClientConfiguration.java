package com.flanks255.simplyutilities.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ClientConfiguration {
    private static final ModConfigSpec.Builder CLIENT_BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec CLIENT_CONFIG;

    public static ModConfigSpec.BooleanValue ZOOMSMOOTHCAM;
    public static ModConfigSpec.DoubleValue ZOOMFOV;

    static {
        CLIENT_BUILDER.comment("Zoom options.").push("zoom");
            ZOOMFOV = CLIENT_BUILDER.comment("Zoomed in FOV value.").defineInRange("zoomfov", 5.0d, 1.0d, 90.0d);
            ZOOMSMOOTHCAM = CLIENT_BUILDER.comment("Smooth camera movement while zoomed").define("zoomsmoothcam", true);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
