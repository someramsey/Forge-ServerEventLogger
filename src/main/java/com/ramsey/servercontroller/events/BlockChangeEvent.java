package com.ramsey.servercontroller.events;

import net.minecraft.core.BlockPos;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class BlockChangeEvent extends Event {
    public String block;
    public BlockPos position;
    public BlockEventType blockEventType;

    public BlockChangeEvent(BlockEventType blockEventType) {
        this.blockEventType = blockEventType;
    }

    @Override
    public EventType getType() {
        return EventType.Block_Change;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);
        outputStream.writeUTF(this.block);
        outputStream.writeInt(this.position.getX());
        outputStream.writeInt(this.position.getY());
        outputStream.writeInt(this.position.getZ());
        outputStream.writeInt(this.blockEventType.ordinal());
    }

    public enum BlockEventType {
        BLOCK_BREAK,
        BLOCK_PLACE
    }
}
