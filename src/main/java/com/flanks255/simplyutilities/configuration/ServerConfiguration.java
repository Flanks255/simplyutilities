package com.flanks255.simplyutilities.configuration;


import net.minecraftforge.common.ForgeConfigSpec;

public class ServerConfiguration {
    private static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec SERVER_CONFIG;

    public static ForgeConfigSpec.IntValue PLAYER_MAX_HOMES;

    static {
        PLAYER_MAX_HOMES = SERVER_BUILDER.comment("Max number of homes per player").defineInRange("playerMaxHomes", 3, 0, 255);

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
