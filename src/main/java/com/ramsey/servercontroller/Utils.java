package com.ramsey.servercontroller;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class Utils {

    public static JsonObject encodeBlockPos(BlockPos blockPos) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("x", blockPos.getX());
        jsonObject.addProperty("y", blockPos.getY());
        jsonObject.addProperty("z", blockPos.getZ());

        return jsonObject;
    }

    public static JsonObject encodeVec3(Vec3 blockPos) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("x", blockPos.x);
        jsonObject.addProperty("y", blockPos.y);
        jsonObject.addProperty("z", blockPos.z);

        return jsonObject;
    }
}
