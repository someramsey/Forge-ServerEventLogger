package com.ramsey.servercontroller.events;

import net.minecraft.core.BlockPos;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class BlockBreakEvent extends Event {
    public String block;
    public BlockPos position;

    @Override
    public EventType getType() {
        return EventType.BLOCK_BREAK;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);
        outputStream.writeUTF(this.block);
        outputStream.writeInt(this.position.getX());
        outputStream.writeInt(this.position.getY());
        outputStream.writeInt(this.position.getZ());
    }
}
