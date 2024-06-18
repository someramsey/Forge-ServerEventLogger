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

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int eventClumpSize;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event)
    {
        eventClumpSize = EVENT_CLUMP_SIZE.get();
    }
}
