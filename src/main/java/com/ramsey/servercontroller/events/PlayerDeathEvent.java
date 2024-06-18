package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.world.phys.Vec3;

public class PlayerDeathEvent extends Event {
    public String cause;
    public String message;
    public Vec3 position;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.PLAYER_DEATH.toString());
        jsonObject.addProperty("cause", cause);
        jsonObject.addProperty("message", message);
        jsonObject.add("position", Utils.encodeVec3(position));
    }
}
