package com.ramsey.servercontroller.events;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Event {
    public String uuid;
    public long timestamp;

    public Event() {
        this.timestamp = System.currentTimeMillis();
    }

    public abstract EventType getType();

    public void write(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeUTF(this.uuid);
        outputStream.writeLong(this.timestamp);
        outputStream.write(this.getType().ordinal());
    }


    public enum EventType {
        PLAYER_JOIN,
        PLAYER_LEAVE,
        PLAYER_DEATH,
        Player_SPAWN,
        PLAYER_CHAT,
        PLAYER_CHANGE_LEVEL,
        BLOCK_PLACE,
        BLOCK_BREAK,
        CONTAINER_INTERACTION
    }
}

