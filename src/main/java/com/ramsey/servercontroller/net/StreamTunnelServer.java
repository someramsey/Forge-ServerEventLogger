package com.ramsey.servercontroller.net;

import com.ramsey.servercontroller.Config;
import com.ramsey.servercontroller.ServerControllerMain;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ConcurrentLinkedQueue;

public class StreamTunnelServer {
    private AsynchronousServerSocketChannel serverChannel;
    private ConcurrentLinkedQueue<ByteBuffer> queue;
    private AsynchronousSocketChannel connection;
    private boolean writing;

    public StreamTunnelServer(int port) {
        try {
            InetSocketAddress address = new InetSocketAddress(port);
            serverChannel = AsynchronousServerSocketChannel.open().bind(address);
            queue = new ConcurrentLinkedQueue<>();

            serverChannel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                @Override
                public void completed(AsynchronousSocketChannel clientChannel, Void attachment) {
                    if (connection != null) {
                        try {
                            clientChannel.close();
                        } catch (IOException exception) {
                            ServerControllerMain.LOGGER.error("Failed to close the client channel", exception);
                        }
                    }
                    connection = clientChannel;
                }

                @Override
                public void failed(Throwable exception, Void attachment) {
                    ServerControllerMain.LOGGER.error("Failed to accept a connection", exception);
                }
            });
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to initialize the stream tunnel server", exception);
        }
    }

    public void write(String input) {
        ByteBuffer data = ByteBuffer.wrap(input.getBytes());
        queue.add(data);
        writeNext();
    }

    private void writeNext() {
        if (writing || connection == null || !connection.isOpen()) {
            return;
        }

        ByteBuffer next = queue.poll();

        if (next != null) {
            writing = true;

            connection.write(next, next, new CompletionHandler<>() {
                @Override
                public void completed(Integer result, ByteBuffer buffer) {
                    if (buffer.hasRemaining()) {
                        connection.write(buffer, buffer, this);
                    } else {
                        writing = false;
                        writeNext();
                    }
                }

                @Override
                public void failed(Throwable exception, ByteBuffer buffer) {
                    ServerControllerMain.LOGGER.error("Failed to write data to the client", exception);

                    writing = false;
                    writeNext();
                }
            });
        }

    }

    public void close() {
        try {
            serverChannel.close();
        } catch (IOException exception) {
            ServerControllerMain.LOGGER.error("Failed to close the server channel", exception);
        }
    }
}