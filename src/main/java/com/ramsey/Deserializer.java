package com.ramsey;

import com.ramsey.servercontroller.events.*;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializer {
    public static Event readEvent(ObjectInputStream objectInputStream) throws IOException {
        int typeOrdinal = objectInputStream.readByte();

        Event.EventType type = Event.EventType.fromOrdinal(typeOrdinal);
        Event event = fromType(type);

        event.read(objectInputStream);

        return event;
    }

    public static Event fromType(Event.EventType type) {
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
