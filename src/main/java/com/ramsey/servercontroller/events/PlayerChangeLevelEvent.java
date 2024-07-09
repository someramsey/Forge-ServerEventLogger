package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class PlayerChangeLevelEvent extends Event {
    public String level;
    public Vec3 position;

    @Override
    public EventType getType() {
        return EventType.PLAYER_CHANGE_LEVEL;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);

        outputStream.writeUTF(level);
        outputStream.writeDouble(position.x);
        outputStream.writeDouble(position.y);
        outputStream.writeDouble(position.z);
    }
}
