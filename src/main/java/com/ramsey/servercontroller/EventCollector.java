package com.ramsey.servercontroller;

import com.ramsey.servercontroller.events.Event;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventCollector {
    private static final ExecutorService executor = Executors.newSingleThreadExecutor();

    private static final LinkedList<Event> events = new LinkedList<>();
    private static OutputStreamWriter stream;
    private static boolean flushing;

    public static void add(Event event) {
        events.add(event);

        if (events.size() > Config.eventClumpSize) {
            flush();
            events.clear();
        }
    }

    private static void flush() {
        if(flushing) {
            return;
        }

        flushing = true;

        executor.execute(() -> {
            try {
                for (Event event : events) {
                    stream.write(event.toString());
                    stream.write("\n");
                }

                stream.flush();
            } catch (IOException exception) {
                ServerControllerMain.LOGGER.error("Failed to flush events", exception);
            }

            flushing = false;
        });
    }

    public static void init() {
        try {
            stream = new OutputStreamWriter(System.out);
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize EventCollector", exception);
        }
    }

    public static void close() {
        try {
            stream.close();
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to close EventCollector", exception);
        }
    }
}
