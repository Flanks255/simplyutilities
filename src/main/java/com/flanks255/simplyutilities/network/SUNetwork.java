package com.flanks255.simplyutilities.network;

import com.flanks255.simplyutilities.SimplyUtilities;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SUNetwork {
    public static final ResourceLocation channelName = new ResourceLocation(SimplyUtilities.MODID, "network");
    public static final String networkVersion = new ResourceLocation(SimplyUtilities.MODID, "1").toString();

    public static SimpleChannel getNetworkChannel() {
        final SimpleChannel channel = NetworkRegistry.ChannelBuilder.named(channelName)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(() -> networkVersion)
                .simpleChannel();

        channel.messageBuilder(OpenOtherDoorMessage.class, 1)
                .decoder(OpenOtherDoorMessage::decode)
                .encoder(OpenOtherDoorMessage::encode)
                .consumer(OpenOtherDoorMessage::handle)
                .add();

        channel.messageBuilder(OpenDebugHandMessage.class, 2)
                .decoder(OpenDebugHandMessage::decode)
                .encoder(OpenDebugHandMessage::encode)
                .consumer(OpenDebugHandMessage::handle)
                .add();

        channel.messageBuilder(ZoomFOVMessage.class, 3)
                .decoder(ZoomFOVMessage::decode)
                .encoder(ZoomFOVMessage::encode)
                .consumer(ZoomFOVMessage::handle)
                .add();

        channel.messageBuilder(ZoomSmoothMessage.class, 4)
                .decoder(ZoomSmoothMessage::decode)
                .encoder(ZoomSmoothMessage::encode)
                .consumer(ZoomSmoothMessage::handle)
                .add();


        return channel;
    }
}
