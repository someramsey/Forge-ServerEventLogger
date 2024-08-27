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
        .define("outputpath", "./logs/");

    private static final ForgeConfigSpec.ConfigValue<String> OUTPUT_FILE = BUILDER
        .comment("The name of the file where the events will be written")
        .define("outputfile", "events.bin");

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String outputPath;
    public static String outputFile;

    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        outputPath = OUTPUT_PATH.get();
        outputFile = OUTPUT_FILE.get();
    }
}
