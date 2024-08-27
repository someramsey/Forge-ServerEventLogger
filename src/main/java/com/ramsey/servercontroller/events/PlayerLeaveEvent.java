package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerLeaveEvent extends Event {
    public Vec3 position;

    @Override
    public EventType getType() {
        return EventType.PLAYER_LEAVE;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);

        this.position = new Vec3(
            inputStream.readDouble(),
            inputStream.readDouble(),
            inputStream.readDouble()
        );
    }
}
