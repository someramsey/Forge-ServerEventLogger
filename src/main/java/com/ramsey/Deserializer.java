package com.ramsey;

import com.ramsey.servercontroller.events.*;
import net.minecraft.world.phys.Vec3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Deserializer {
    private static final ArrayList<Event> events = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readFile("data/events.bin");


        System.out.println(events.size());

        final Vec3 targetPos = new Vec3(-744, 82, -30);

        HashMap<String, String> players = new HashMap<>();

        for (Event event : events) {
            if (event instanceof PlayerJoinEvent pEvent) {
                players.put(pEvent.uuid, pEvent.name);
            } else if (event instanceof ContainerInteractionEvent pEvent) {

                double distance = pEvent.playerPosition.distanceTo(targetPos);

                if (distance < 10) {
                    Date date = new Date(pEvent.timestamp);
                    String dateAsString = date.toString();

                    StringBuilder changesBuilder = new StringBuilder();

                    for (var change : pEvent.changes) {
                        changesBuilder.append(change.item).append(", ");
                    }

                    System.out.println(players.get(pEvent.uuid) + " | " + dateAsString + "|" + changesBuilder.toString());

                }

//                pEvent.changes.forEach(change -> {
//                    if (change.item.equals("createdeco:zinc_coin")) {
//                        Date date = new Date(pEvent.timestamp);
//                        System.out.println(players.get(pEvent.uuid) + " | " + change.item + " | " + change.count + "| ");
//                        double distance = pEvent.playerPosition.distanceTo(targetPos);
//                        System.out.println(distance);
//                    }
//                });

            }
        }
    }

    private static void readFile(String path) throws IOException {
        File file = new File(path);

        try (
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        ) {
            while (fileInputStream.available() > 0) {
                readEvent(objectInputStream);
            }
        }
    }

    private static void readEvent(ObjectInputStream objectInputStream) throws IOException {
        int typeOrdinal = objectInputStream.readByte();

        Event.EventType type = Event.EventType.fromOrdinal(typeOrdinal);
        Event event = fromType(type);

        event.read(objectInputStream);

        events.add(event);
    }

    private static Event fromType(Event.EventType type) {
        return switch (type) {
            case PLAYER_JOIN -> new PlayerJoinEvent();
            case PLAYER_LEAVE -> new PlayerLeaveEvent();
            case PLAYER_DEATH -> new PlayerDeathEvent();
            case PLAYER_CHAT -> new PlayerChatEvent();
            case Player_SPAWN -> new PlayerSpawnEvent();
            case PLAYER_CHANGE_LEVEL -> new PlayerChangeLevelEvent();
            case Container_Interaction -> new ContainerInteractionEvent();
            case Block_Change -> new BlockChangeEvent();
        };
    }
}
