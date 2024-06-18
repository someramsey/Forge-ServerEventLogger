package com.ramsey.servercontroller.events;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ramsey.servercontroller.Utils;
import net.minecraft.world.phys.Vec3;

public class ContainerInteractionEvent extends Event {
    public String containerBlock;
    public Vec3 position;
    SlotChanges[] changes;

    @Override
    protected void encode(JsonObject jsonObject) {
        jsonObject.addProperty("type", EventType.CONTAINER_INTERACTION.toString());
        jsonObject.addProperty("container", containerBlock);
        jsonObject.add("position", Utils.encodeVec3(position));
        jsonObject.add("changes", encodeChanges());
    }

    private JsonElement encodeChanges() {
        JsonObject changesObject = new JsonObject();

        for (SlotChanges change : changes) {
            JsonObject changeObject = new JsonObject();

            changeObject.addProperty("slot", change.slot);
            changeObject.addProperty("item", change.item);
            changeObject.addProperty("count", change.count);

            changesObject.add(String.valueOf(change.slot), changeObject);
        }

        return changesObject;
    }

    public static class SlotChanges {
        int slot;
        public String item;
        int count;
    }
}
