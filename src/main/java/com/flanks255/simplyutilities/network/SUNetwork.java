package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;


public class SUNetwork {
    public static void register(final RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar reg = event.registrar(SimplyUtilities.MODID);

        reg.play(ZoomFOVPacket.ID, ZoomFOVPacket::new, handler -> handler.client(ZoomFOVPacket::handle));
        reg.play(ZoomSmoothMessage.ID, ZoomSmoothMessage::new, handler -> handler.client(ZoomSmoothMessage::handle));
        reg.play(OpenOtherDoorPacket.ID, OpenOtherDoorPacket::new, handler -> handler.server(OpenOtherDoorPacket::handle));
        reg.play(OpenDebugHandPacket.ID, OpenDebugHandPacket::new, handler -> handler.client(OpenDebugHandPacket::handle));
    }
}
