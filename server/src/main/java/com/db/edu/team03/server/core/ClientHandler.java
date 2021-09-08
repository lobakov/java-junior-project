package com.db.edu.team03.server.core;

import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.MessageHandler;

import java.io.*;
import java.net.Socket;

/**
 * ServerCore - class that works with client connection
 */
public class ClientHandler implements Runnable {

    private final Socket socket;
    private final DataOutputStream output;
    private final MessageHandler messageHandler;

    public ClientHandler(Socket socket, MessageHandler handler) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.messageHandler = handler;
    }

    /**
     * method that sends message to client
     *
     * @param message - message body
     */
    public void sendMessageToClient(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String ipAddress = socket.getRemoteSocketAddress().toString();

        try (
                final DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()))
        ) {
            while (!Thread.currentThread().isInterrupted()) {
                final String message = input.readUTF();
                messageHandler.accept(ipAddress, message);

            }

        } catch (IOException | ServerException e) {
            System.out.println("Client disconnected.");
            ServerCore.getClientsHandlerMap().removeClient(ipAddress);
            messageHandler.removeUserNickname(ipAddress);
        }
    }
}
