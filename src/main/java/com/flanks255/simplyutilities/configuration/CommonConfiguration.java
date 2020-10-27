package com.flanks255.simplyutilities.configuration;


import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfiguration {
    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    public static ForgeConfigSpec COMMON_CONFIG;

    public static ForgeConfigSpec.BooleanValue RECIPE_FLESH_LEATHER;
    public static ForgeConfigSpec.BooleanValue RECIPE_LOG_STICK;
    public static ForgeConfigSpec.BooleanValue RECIPE_LOG_CHESTS;
    public static ForgeConfigSpec.BooleanValue FIXES_DOUBLEDOORFIX;
    public static ForgeConfigSpec.BooleanValue FIXES_FORCEDOUBLEDOORFIX;

    public static ForgeConfigSpec.IntValue ENDERINHIBITOR_RANGE;
    public static ForgeConfigSpec.BooleanValue ENDERINHIBITOR_ENABLE;
    public static ForgeConfigSpec.BooleanValue ENDERINHIBITOR_PLAYERS;

    public static ForgeConfigSpec.BooleanValue EXO_LEGGINGS;

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


        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
