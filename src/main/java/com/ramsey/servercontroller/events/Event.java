package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;

import java.io.Serializable;

public abstract class Event implements Serializable {
    public String uuid;
    public long timestamp;

    public String encode() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("uuid", uuid);
        jsonObject.addProperty("timestamp", timestamp);

        encode(jsonObject);

        return jsonObject.toString();
    }

    protected abstract void encode(JsonObject jsonObject);

    public enum EventType {
        PLAYER_JOIN,
        PLAYER_LEAVE,
        PLAYER_DEATH,
        PLAYER_CHAT,
        PLAYER_CHANGE_LEVEL,
        BLOCK_PLACE,
        BLOCK_BREAK,
        CONTAINER_INTERACTION;
    }
}


