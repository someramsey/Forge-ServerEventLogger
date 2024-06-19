package com.ramsey.servercontroller.net;

import com.ramsey.servercontroller.ServerControllerMain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.Future;

public class StreamTunnelClient {
    private final AsynchronousSocketChannel clientChannel;

    public StreamTunnelClient(String host, int port) throws IOException {
        clientChannel = AsynchronousSocketChannel.open();
        Future<Void> result = clientChannel.connect(new InetSocketAddress(host, port));

        try {
            result.get(); // Wait until the connection is done
            System.out.println("Connected to the server successfully");
        } catch (Exception exception) {
            ServerControllerMain.LOGGER.error("Failed to connect to the server", exception);
        }

        readFromServer();
    }

    private void readFromServer() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        clientChannel.read(buffer, buffer, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                attachment.flip();
                byte[] data = new byte[attachment.remaining()];
                attachment.get(data);
                System.out.println("Received from server: " + new String(data));
                attachment.clear();
                readFromServer();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("Failed to read from server");
            }
        });
    }

    public void close() throws IOException {
        clientChannel.close();
    }
}