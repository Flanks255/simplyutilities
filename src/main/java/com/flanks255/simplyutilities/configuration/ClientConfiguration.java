package com.flanks255.simplyutilities.configuration;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfiguration {
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec CLIENT_CONFIG;

    public static ForgeConfigSpec.BooleanValue ZOOMSMOOTHCAM;
    public static ForgeConfigSpec.DoubleValue ZOOMFOV;

    static {
        CLIENT_BUILDER.comment("Zoom options.").push("zoom");
            ZOOMFOV = CLIENT_BUILDER.comment("Zoomed in FOV value.").defineInRange("zoomfov", 5.0d, 1.0d, 180.0d);
            ZOOMSMOOTHCAM = CLIENT_BUILDER.comment("Smooth camera movement while zoomed").define("zoomsmoothcam", true);
        CLIENT_BUILDER.pop();

        CLIENT_CONFIG = CLIENT_BUILDER.build();
    }
}
