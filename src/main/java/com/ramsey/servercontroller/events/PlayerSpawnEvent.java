package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerSpawnEvent extends Event {
    public Vec3 position;
    public String level;

    @Override
    public EventType getType() {
        return EventType.Player_SPAWN;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeUTF(level);
        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);

        this.level = inputStream.readUTF();
        this.position = new Vec3(
            inputStream.readDouble(),
            inputStream.readDouble(),
            inputStream.readDouble()
        );
    }
}
