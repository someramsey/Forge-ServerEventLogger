package com.ramsey.servercontroller.events;

import com.ramsey.servercontroller.listeners.ContainerEventListener;
import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ContainerInteractionEvent extends Event {
    public Vec3 playerPosition;
    public LinkedList<ContainerEventListener.ContainerInteraction.SlotChange> changes;

    @Override
    public EventType getType() {
        return EventType.CONTAINER_INTERACTION;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeDouble(playerPosition.x);
        outputStream.writeDouble(playerPosition.y);
        outputStream.writeDouble(playerPosition.z);

        writeChanges(outputStream);
    }

    private void writeChanges(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(changes.size());

        for (ContainerEventListener.ContainerInteraction.SlotChange change : changes) {
            outputStream.writeInt(change.slot);
            outputStream.writeUTF(change.item);
            outputStream.writeInt(change.count);
        }
    }
}
