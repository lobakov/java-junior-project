package com.db.edu.team03.server.core;

import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.MessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.Socket;

/**
 * ServerCore - class that works with client connection
 */
public class ClientHandler implements Runnable {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class);

    private final Socket socket;
    private final DataOutputStream output;
    private final MessageHandler messageHandler;

    public ClientHandler(Socket socket, MessageHandler handler) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.messageHandler = handler;
    }

    /**
     *  method that sends message to client
     *  @param message - message body
     */
    public void sendMessageToClient(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            logger.error(e);
        }
    }

    @Override
    public void run() {
        String IpAddress = socket.getRemoteSocketAddress().toString();

        try (
                final DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()))
        ) {
            while (!Thread.currentThread().isInterrupted()) {
                final String message = input.readUTF();
                messageHandler.accept(IpAddress, message);
            }

        } catch (IOException | ServerException e) {
            System.out.println("Client disconnected.");
            ServerCore.getClientsHandlerMap().removeClient(IpAddress);
        }
    }
}
