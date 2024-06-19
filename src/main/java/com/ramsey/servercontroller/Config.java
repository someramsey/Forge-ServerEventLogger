package com.ramsey.servercontroller;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue EVENT_CLUMP_SIZE = BUILDER
        .comment("Determines how many events are in a single batch")
        .defineInRange("eventClumpSize", 10, 0, Integer.MAX_VALUE);

    private static final ForgeConfigSpec.ConfigValue<String> EVENT_STREAM_OUTPUT_PATH = BUILDER
        .comment("The path to the file where the events will be written")
        .define("eventStreamOutputPath", "./events.json");

    private static final ForgeConfigSpec.IntValue STREAM_TUNNEL_LISTN_PORT = BUILDER
        .comment("The port on which the stream tunnel server will listen")
        .defineInRange("streamTunnelPort", 25500, 0, 65535);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int eventClumpSize;
    public static String eventStreamOutputPath;
    public static int streamTunnelPort;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event)
    {
        eventClumpSize = EVENT_CLUMP_SIZE.get();
        eventStreamOutputPath = EVENT_STREAM_OUTPUT_PATH.get();
        streamTunnelPort = STREAM_TUNNEL_LISTN_PORT.get();
    }
}
