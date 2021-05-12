package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.commands.debug.DebugScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class ClientNetProxy extends CommonNetProxy {
    @Override
    public void handleDebugHandMessage(PlayerEntity player, OpenDebugHandMessage message) {
            Minecraft.getInstance().displayGuiScreen(new DebugScreen(message.getItemStack()));
    }
}
