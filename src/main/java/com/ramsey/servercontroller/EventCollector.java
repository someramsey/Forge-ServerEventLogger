package com.ramsey.servercontroller;

import com.ramsey.servercontroller.events.Event;

import java.io.*;

public class EventCollector {
    private static Event activeEvent;
    private static FileOutputStream fileOutputStream;

    private static void merge(Event event) {
        if (activeEvent == null) {
            activeEvent = event;
            return;
        }

        //TODO: compress events

    }



    public static void record(Event event) {
        merge(event);

        try (
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)
        ) {
            activeEvent.write(objectOutputStream);
            fileOutputStream.write(byteOutputStream.toByteArray());
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to write event to output stream", exception);
        }
    }

    public static void init() {
        try {
            fileOutputStream = new FileOutputStream(Config.eventStreamOutputPath, true);
//            streamTunnelServer = new StreamTunnelServer(Config.streamTunnelListenPort);
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize EventCollector", exception);
        }
    }

    public static void close() {
        try {
            fileOutputStream.close();
//            streamTunnelServer.close();
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to close EventCollector", exception);
        }
    }
}
