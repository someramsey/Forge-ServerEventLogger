package com.ramsey.servercontroller;

import com.google.gson.Gson;
import com.ramsey.servercontroller.events.Event;
import com.ramsey.servercontroller.net.StreamTunnelServer;

import java.io.FileWriter;
import java.io.IOException;

public class EventCollector {
    private static final String[] events = new String[Config.eventClumpSize];
    private static int size;

    private static final Gson gson = new Gson();
    private static FileWriter fileWriter;
    private static StreamTunnelServer streamTunnelServer;

    public static void add(Event event) {
        String eventData = gson.toJson(event.encode());

        streamTunnelServer.write(eventData);

        events[size++] = eventData;

        if (size == Config.eventClumpSize - 1) {
            flush();
            size = 0;
        }
    }

    private static void flush() {
        try {
            for (int i = 0; i < size; i++) {
                fileWriter.write(events[i]);
                fileWriter.write("\n");
            }

            fileWriter.flush();
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to flush events", exception);
        }
    }

    public static void init() {
        try {
            fileWriter = new FileWriter(Config.eventStreamOutputPath);
            streamTunnelServer = new StreamTunnelServer(Config.streamTunnelListenPort);
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize EventCollector", exception);
        }
    }

    public static void close() {
        try {
            fileWriter.close();
            streamTunnelServer.close();
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to close EventCollector", exception);
        }
    }
}
