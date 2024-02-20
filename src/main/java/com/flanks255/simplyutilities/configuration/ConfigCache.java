package com.flanks255.simplyutilities.configuration;

import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;

public class ConfigCache {
    public static void RefreshClientCache() {
        zoom_fov = ClientConfiguration.ZOOMFOV.get();
        zoom_smooth = ClientConfiguration.ZOOMSMOOTHCAM.get();
    }

    public static void RefreshCommonCache() {
        EnderInhibitorEnabled = CommonConfiguration.ENDERINHIBITOR_ENABLE.get();
        EnderInhibitorRange = CommonConfiguration.ENDERINHIBITOR_RANGE.get();
        EnderInhibitorPlayers = CommonConfiguration.ENDERINHIBITOR_PLAYERS.get();

        doorFix = CommonConfiguration.FIXES_DOUBLEDOORFIX.get();
        forceDoorFix = CommonConfiguration.FIXES_FORCEDOUBLEDOORFIX.get();

        cmd_bed = CommonConfiguration.CMD_BED.get();
        cmd_spawn = CommonConfiguration.CMD_SPAWN.get();
        cmd_home = CommonConfiguration.CMD_HOME.get();
    }

    public static void listen(ModConfigEvent event) {
        if (event.getConfig().getType() == ModConfig.Type.COMMON)
            RefreshCommonCache();
        if (event.getConfig().getType() == ModConfig.Type.CLIENT)
            RefreshClientCache();
    }

    public static boolean EnderInhibitorEnabled;
    public static int EnderInhibitorRange;
    public static boolean EnderInhibitorPlayers;
    public static boolean doorFix = true;
    public static boolean forceDoorFix = false;

    public static boolean cmd_bed = true;
    public static boolean cmd_spawn = true;
    public static boolean cmd_home = true;

    public static double zoom_fov = 5.0d;
    public static boolean zoom_smooth = true;
}
