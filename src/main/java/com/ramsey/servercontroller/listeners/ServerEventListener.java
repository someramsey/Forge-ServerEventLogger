package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventListener {
    @SubscribeEvent
    public static void serverStartEvent(ServerStartedEvent serverEvent) {
        EventCollector.init();
    }

    @SubscribeEvent
    public static void serverStopEvent(ServerStoppedEvent serverEvent) {
        EventCollector.close();
    }
}
