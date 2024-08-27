package com.ramsey.servercontroller.events;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerChatEvent extends Event {
    public String message;

    @Override
    public EventType getType() {
        return EventType.PLAYER_CHAT;
    }

    @Override
    public void write(ObjectOutputStream outputStream) throws IOException {
        super.write(outputStream);
        outputStream.writeUTF(message);
    }

    @Override
    public void read(ObjectInputStream inputStream) throws IOException {
        super.read(inputStream);
        this.message = inputStream.readUTF();
    }
}
