package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.commands.debug.DebugScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientPacketStuff {
    public static void handleDebugHandMessage(Player player, OpenDebugHandPacket message) {
        Minecraft.getInstance().setScreen(new DebugScreen(message.getItemStack()));
    }
}
