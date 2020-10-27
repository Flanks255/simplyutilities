package com.flanks255.simplyutilities.configuration;

public class ConfigCache {
    public static void RefreshCache() {
        EnderInhibitorEnabled = CommonConfiguration.ENDERINHIBITOR_ENABLE.get();
        EnderInhibitorRange = CommonConfiguration.ENDERINHIBITOR_RANGE.get();
        EnderInhibitorPlayers = CommonConfiguration.ENDERINHIBITOR_PLAYERS.get();

        doorFix = CommonConfiguration.FIXES_DOUBLEDOORFIX.get();
        forceDoorFix = CommonConfiguration.FIXES_FORCEDOUBLEDOORFIX.get();
    }

    public static boolean EnderInhibitorEnabled;
    public static int EnderInhibitorRange;
    public static boolean EnderInhibitorPlayers;
    public static boolean doorFix = true;
    public static boolean forceDoorFix = false;
}
