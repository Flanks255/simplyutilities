package com.flanks255.simplyutilities.configuration;

public class ConfigCache {
    public static void RefreshCache() {
        EnderInhibitorEnabled = CommonConfiguration.ENDERINHIBITOR_ENABLE.get();
        EnderInhibitorRange = CommonConfiguration.ENDERINHIBITOR_RANGE.get();
        EnderInhibitorPlayers = CommonConfiguration.ENDERINHIBITOR_PLAYERS.get();

        doorFix = CommonConfiguration.FIXES_DOUBLEDOORFIX.get();
        forceDoorFix = CommonConfiguration.FIXES_FORCEDOUBLEDOORFIX.get();

        cmd_bed = CommonConfiguration.CMD_BED.get();
        cmd_spawn = CommonConfiguration.CMD_SPAWN.get();
        cmd_home = CommonConfiguration.CMD_HOME.get();
    }

    public static boolean EnderInhibitorEnabled;
    public static int EnderInhibitorRange;
    public static boolean EnderInhibitorPlayers;
    public static boolean doorFix = true;
    public static boolean forceDoorFix = false;

    public static boolean cmd_bed = true;
    public static boolean cmd_spawn = true;
    public static boolean cmd_home = true;
}
