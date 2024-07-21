package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;


public class SUNetwork {
    public static void register(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar reg = event.registrar(SimplyUtilities.MODID);

        reg.playToClient(ZoomFOVPacket.TYPE, ZoomFOVPacket.CODEC,ZoomFOVPacket::handle);
        reg.playToClient(ZoomSmoothMessage.TYPE, ZoomSmoothMessage.CODEC, ZoomSmoothMessage::handle);
        reg.playToServer(OpenOtherDoorPacket.TYPE, OpenOtherDoorPacket.CODEC, OpenOtherDoorPacket::handle);
        reg.playToClient(OpenDebugHandPacket.TYPE, OpenDebugHandPacket.CODEC, OpenDebugHandPacket::handle);
    }
}
