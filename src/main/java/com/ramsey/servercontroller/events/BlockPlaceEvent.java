package com.ramsey.servercontroller.events;

import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.core.BlockPos;

public class BlockPlaceEvent extends Event {
    public String block;
    public BlockPos position;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.BLOCK_PLACE.toString());
        jsonObject.addProperty("block", block);
        jsonObject.add("position", Utils.encodeBlockPos(position));
    }
}
