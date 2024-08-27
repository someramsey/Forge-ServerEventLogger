package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class ContainerInteractionEvent extends Event {
    public Vec3 playerPosition;
    public LinkedList<SlotChange> changes;

    @Override
    public EventType getType() {
        return EventType.Container_Interaction;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeDouble(playerPosition.x);
        outputStream.writeDouble(playerPosition.y);
        outputStream.writeDouble(playerPosition.z);

        writeChanges(outputStream);
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);

        this.playerPosition = new Vec3(
            inputStream.readDouble(),
            inputStream.readDouble(),
            inputStream.readDouble()
        );

        this.changes = readChanges(inputStream);
    }

    private void writeChanges(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeInt(changes.size());

        for (SlotChange change : changes) {
            outputStream.writeUTF(change.item);
            outputStream.writeInt(change.slot);
            outputStream.writeInt(change.count);
        }
    }

    private LinkedList<SlotChange> readChanges(ObjectInputStream inputStream) throws IOException {
        int size = inputStream.readInt();
        LinkedList<SlotChange> changes = new LinkedList<>();

        for (int i = 0; i < size; i++) {
            String item = inputStream.readUTF();
            int slot = inputStream.readInt();
            int count = inputStream.readInt();

            changes.add(new SlotChange(item, slot, count));
        }

        return changes;
    }

    public static class SlotChange {
        public final String item;
        public final int slot;
        public final int count;

        public SlotChange(String item, int slot, int count) {
            this.item = item;
            this.slot = slot;
            this.count = count;
        }
    }
}
