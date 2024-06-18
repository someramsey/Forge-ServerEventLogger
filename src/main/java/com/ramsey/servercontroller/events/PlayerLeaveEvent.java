package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.world.phys.Vec3;

public class PlayerLeaveEvent extends Event {
    public Vec3 position;


    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.PLAYER_LEAVE.toString());
        jsonObject.add("position", Utils.encodeVec3(position));
    }
}
