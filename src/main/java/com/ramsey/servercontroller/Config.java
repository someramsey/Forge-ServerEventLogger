package com.ramsey.servercontroller;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<String> OUTPUT_PATH = BUILDER
        .comment("The path to the file where the events will be written")
        .define("eventStreamOutputPath", "./events.json");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String eventStreamOutputPath;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        eventStreamOutputPath = OUTPUT_PATH.get();
    }
}
