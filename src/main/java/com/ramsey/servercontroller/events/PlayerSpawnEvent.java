package com.ramsey.servercontroller.events;

import net.minecraft.world.phys.Vec3;

import java.io.IOException;
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
    }
}
