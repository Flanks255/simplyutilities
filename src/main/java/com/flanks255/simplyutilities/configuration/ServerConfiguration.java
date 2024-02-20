package com.flanks255.simplyutilities.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfiguration {
    private static final ModConfigSpec.Builder SERVER_BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec SERVER_CONFIG;

    public static ModConfigSpec.IntValue PLAYER_MAX_HOMES;

    static {
        PLAYER_MAX_HOMES = SERVER_BUILDER.comment("Max number of homes per player").defineInRange("playerMaxHomes", 3, 0, 255);

        SERVER_CONFIG = SERVER_BUILDER.build();
    }
}
