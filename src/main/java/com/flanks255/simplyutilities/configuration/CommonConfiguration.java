package com.flanks255.simplyutilities.configuration;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfiguration {
    private static final ModConfigSpec.Builder COMMON_BUILDER = new ModConfigSpec.Builder();
    public static ModConfigSpec COMMON_CONFIG;

    public static ModConfigSpec.BooleanValue RECIPE_FLESH_LEATHER;
    public static ModConfigSpec.BooleanValue RECIPE_LOG_STICK;
    public static ModConfigSpec.BooleanValue RECIPE_LOG_CHESTS;
    public static ModConfigSpec.BooleanValue FIXES_DOUBLEDOORFIX;
    public static ModConfigSpec.BooleanValue FIXES_FORCEDOUBLEDOORFIX;

    public static ModConfigSpec.IntValue ENDERINHIBITOR_RANGE;
    public static ModConfigSpec.BooleanValue ENDERINHIBITOR_ENABLE;
    public static ModConfigSpec.BooleanValue ENDERINHIBITOR_PLAYERS;

    public static ModConfigSpec.BooleanValue EXO_LEGGINGS;

    public static ModConfigSpec.BooleanValue ONLINE_DETECTOR;

    public static ModConfigSpec.BooleanValue CMD_BED;
    public static ModConfigSpec.BooleanValue CMD_SPAWN;
    public static ModConfigSpec.BooleanValue CMD_HOME;

    static {
        COMMON_BUILDER.comment("Convenience Recipes").push("recipes");
            RECIPE_FLESH_LEATHER = COMMON_BUILDER.comment("Enable Smelting rotton flesh into leather").define("smeltFleshIntoLeather", true);
            RECIPE_LOG_STICK = COMMON_BUILDER.comment("Enable crafting sticks from logs").define("craftLogsToSticks", true);
            RECIPE_LOG_CHESTS = COMMON_BUILDER.comment("Enable crafting chests from logs").define("craftLogsToChests", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Double door fix").push("doubledoor");
            FIXES_DOUBLEDOORFIX = COMMON_BUILDER.comment("Double doors open at same time").define("fixDoubleDoors", true);
            FIXES_FORCEDOUBLEDOORFIX = COMMON_BUILDER.comment("Force double door fix even if quark is loaded").define("forceFixDoubleDoors", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Ender Inhibitor").push("enderinhibitor");
            ENDERINHIBITOR_ENABLE = COMMON_BUILDER.comment("Enable the Ender Inhibitor").define("enderInhibitor", true);
            ENDERINHIBITOR_RANGE = COMMON_BUILDER.comment("Prevent Ender teleportation inside this range.").defineInRange("enderInhibitorRange", 16,1, 64);
            ENDERINHIBITOR_PLAYERS = COMMON_BUILDER.comment("Does this apply to players?").define("enderInhibitorPlayers", false);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Exoskeleton").push("exoskeleton");
            EXO_LEGGINGS = COMMON_BUILDER.comment("Enable the Exoskeleton Leggings").comment("Absorbs fall damage.").define("exoleggings", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Online Detector").push("onlinedetector");
        ONLINE_DETECTOR = COMMON_BUILDER.comment("Enable the Online Detector").comment("Emits redstone when the placing player is online").define("online_detector", true);
        COMMON_BUILDER.pop();

        COMMON_BUILDER.comment("Commands").push("commands");
            CMD_BED = COMMON_BUILDER.comment("Enable teleporting to the last slept in bed.").define("bed", true);
            CMD_SPAWN = COMMON_BUILDER.comment("Enable teleporting to the spawn point.").define("spawn", true);
            CMD_HOME = COMMON_BUILDER.comment("Enable home commands").define("home", true);
        COMMON_BUILDER.pop();

        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
