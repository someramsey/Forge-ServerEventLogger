package com.ramsey.servercontroller.events;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.Serializable;

public abstract class Event implements Serializable {
    public String uuid;
    public long timestamp;

    public Event() {
        this.timestamp = System.currentTimeMillis();
    }

    public JsonObject encode() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", uuid);
        jsonObject.addProperty("timestamp", timestamp);

        encode(jsonObject);

        return jsonObject;
    }

    protected abstract void encode(JsonObject jsonObject);

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


