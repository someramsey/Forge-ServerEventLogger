package com.ramsey.servercontroller.net;

import com.ramsey.servercontroller.ServerControllerMain;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientConnection {
    public final AsynchronousSocketChannel clientChannel;

    public ClientConnection(AsynchronousSocketChannel clientChannel) {
        this.clientChannel = clientChannel;
    }

    public void write(ByteBuffer data) {
        clientChannel.write(data, data, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer buffer) {
                if (buffer.hasRemaining()) {
                    clientChannel.write(buffer, buffer, this);
                }
            }

            @Override
            public void failed(Throwable exc, ByteBuffer buffer) {
                ServerControllerMain.LOGGER.error("Failed to write data to client", exc);
            }
        });
    }
}