package com.ramsey.servercontroller;

import com.ramsey.servercontroller.events.Event;

import java.util.LinkedList;

public class EventCollector {
    private static final LinkedList<Event> events = new LinkedList<>();
    private static int size;

    public static void add(Event event) {

        events.add(event);
        size++;

        if(size > Config.eventClumpSize) {
            flush(); //TODO: async
            size = 0;
        }
    }

    private static void flush() {
        try {
            for (Event event : events) {
                System.out.println(event.encode());
            }

        } catch (Error error) {
            System.out.println(error.getMessage());
            error.printStackTrace();
        }
    }
}
