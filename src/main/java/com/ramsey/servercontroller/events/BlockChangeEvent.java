package com.ramsey.servercontroller.events;

import net.minecraft.core.BlockPos;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BlockChangeEvent extends Event {
    public String block;
    public BlockPos position;
    public BlockEventType blockEventType;

    public BlockChangeEvent() {
        super();
    }

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

        outputStream.write(this.blockEventType.ordinal());
        outputStream.writeUTF(this.block);
        outputStream.writeDouble(this.position.getX());
        outputStream.writeDouble(this.position.getY());
        outputStream.writeDouble(this.position.getZ());
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);

        this.blockEventType = BlockEventType.fromOrdinal(inputStream.read());
        this.block = inputStream.readUTF();
        this.position = new BlockPos(
            inputStream.readDouble(),
            inputStream.readDouble(),
            inputStream.readDouble()
        );
    }

    public enum BlockEventType {
        BLOCK_BREAK,
        BLOCK_PLACE;

        private static final BlockEventType[] valueCache = values();

        public static BlockEventType fromOrdinal(int ordinal) {
            return valueCache[ordinal];
        }
    }
}
