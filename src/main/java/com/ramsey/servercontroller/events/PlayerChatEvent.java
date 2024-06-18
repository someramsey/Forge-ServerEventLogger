package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;

public class PlayerChatEvent extends Event {
    public String message;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.PLAYER_CHAT.toString());
        jsonObject.addProperty("message", message);
    }
}
