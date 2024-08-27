package com.ramsey.servercontroller.events;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Event {
    public String uuid;
    public long timestamp;

    public Event() {
        this.timestamp = System.currentTimeMillis();
    }

    public abstract EventType getType();

    public void write(ObjectOutputStream outputStream) throws IOException {
        outputStream.write(this.getType().ordinal());
        outputStream.writeUTF(this.uuid);
        outputStream.writeLong(this.timestamp);
    }

    public void read(ObjectInputStream inputStream) throws IOException {
        this.uuid = inputStream.readUTF();
        this.timestamp = inputStream.readLong();
    }

    public enum EventType {
        PLAYER_JOIN,
        PLAYER_LEAVE,
        PLAYER_DEATH,
        Player_SPAWN,
        PLAYER_CHAT,
        PLAYER_CHANGE_LEVEL,
        Block_Change,
        Container_Interaction;

        private static final EventType[] valueCache = values();

        public static EventType fromOrdinal(int ordinal) {
            return valueCache[ordinal];
        }
    }
}

