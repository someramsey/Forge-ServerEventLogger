package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class ContainerInteractionEvent extends Event {
    public String containerBlock;
    public Vec3 position;
    SlotChanges[] changes;


    @Override
    public EventType getType() {
        return EventType.CONTAINER_INTERACTION;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeUTF(containerBlock);
        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
        outputStream.writeInt(changes.length);

        writeChanges(outputStream);
    }

    private void writeChanges(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(changes.length);

        for (SlotChanges change : changes) {
            outputStream.writeInt(change.slot);
            outputStream.writeUTF(change.item);
            outputStream.writeInt(change.count);
        }
    }

    public static class SlotChanges {
        public String item;
        int slot;
        int count;
    }
}
