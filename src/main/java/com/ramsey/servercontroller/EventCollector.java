package com.ramsey.servercontroller;

import com.ramsey.servercontroller.events.Event;

import java.io.*;

public class EventCollector {
    private static ObjectOutputStream objectOutputStream;

    public static void record(Event event) {
        try {
            event.write(objectOutputStream);
            objectOutputStream.flush();
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to write", exception);
        }
    }

    public static void init() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(Config.eventStreamOutputPath, true);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            ServerControllerMain.LOGGER.info("EventCollector initialized");
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize EventCollector", exception);
        }
    }

    public static void close() {
        try {
            objectOutputStream.close();
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to close EventCollector", exception);
        }
    }
}
