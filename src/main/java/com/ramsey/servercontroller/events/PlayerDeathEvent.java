package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayerDeathEvent extends Event {
    public String cause;
    public Vec3 position;

    @Override
    public EventType getType() {
        return EventType.PLAYER_DEATH;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeUTF(cause);
        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
    }
}
