package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.world.phys.Vec3;

public class PlayerJoinEvent extends Event {
    public String name;
    public String level;
    public Vec3 position;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.PLAYER_JOIN.toString());
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("level", level);
        jsonObject.add("position", Utils.encodeVec3(position));
    }
}
