package com.ramsey.servercontroller;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(ServerControllerMain.MODID)
public class ServerControllerMain {
    public static final String MODID = "servercontroller";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ServerControllerMain() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
