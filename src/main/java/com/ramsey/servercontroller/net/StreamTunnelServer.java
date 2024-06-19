package com.ramsey.servercontroller.net;

import com.ramsey.servercontroller.Config;
import com.ramsey.servercontroller.ServerControllerMain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CopyOnWriteArrayList;

public class StreamTunnelServer {
    private static AsynchronousServerSocketChannel serverChannel;
    private static final CopyOnWriteArrayList<ClientConnection> connections = new CopyOnWriteArrayList<>();

    public static void init() throws IOException {
        serverChannel = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(Config.streamTunnelListenPort));
        acceptConnection();
    }

    public static void close() {
        try {
            serverChannel.close();
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to close the server channel", exception);
        }
    }

    private static void acceptConnection() {
        serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
            @Override
            public void completed(AsynchronousSocketChannel clientChannel, Void attachment) {
                ClientConnection connection = new ClientConnection(clientChannel);
                connections.add(connection);
                acceptConnection();
            }

            @Override
            public void failed(Throwable exception, Void attachment) {
                ServerControllerMain.LOGGER.error("Failed to accept a connection", exception);
            }
        });
    }

    public static void broadcast(ByteBuffer data) {
        for (ClientConnection connection : connections) {
            connection.write(data);
        }
    }
}