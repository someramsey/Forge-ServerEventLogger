package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.world.phys.Vec3;

public class PlayerSpawnEvent extends Event {
    public Vec3 position;
    public String level;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.Player_SPAWN.toString());
        jsonObject.addProperty("level", level);
        jsonObject.add("position", Utils.encodeVec3(position));
    }
}
