package com.ramsey.servercontroller.listeners;

import com.ramsey.servercontroller.Config;
import com.ramsey.servercontroller.EventCollector;
import com.ramsey.servercontroller.ServerControllerMain;
import com.ramsey.servercontroller.net.StreamTunnelClient;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = ServerControllerMain.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEventListener {
    @SubscribeEvent
    public static void serverStartEvent(ServerStartedEvent serverEvent) throws IOException {
        EventCollector.init();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
                new StreamTunnelClient("127.0.0.1", Config.streamTunnelPort);
            } catch (InterruptedException | IOException exception) {
                throw new RuntimeException(exception);
            }
        }).start();
    }

    @SubscribeEvent
    public static void serverStopEvent(ServerStoppedEvent serverEvent) {
        EventCollector.close();
    }
}
