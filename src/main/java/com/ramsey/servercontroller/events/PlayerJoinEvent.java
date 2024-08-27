package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerJoinEvent extends Event {
    public String name;
    public String level;
    public Vec3 position;

    @Override
    public EventType getType() {
        return EventType.PLAYER_JOIN;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeUTF(name);
        outputStream.writeUTF(level);
        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);

        this.name = inputStream.readUTF();
        this.level = inputStream.readUTF();
        this.position = new Vec3(
            inputStream.readDouble(),
            inputStream.readDouble(),
            inputStream.readDouble()
        );
    }
}
