package com.db.edu.team03.server.core;

import com.db.edu.team03.server.handler.MessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ServerCore - class that listens on ports and waits for clients.
 */
public class ServerCore {
    private static final ClientHandlerMap clients = new ClientHandlerMap();

    private MessageHandler messageHandler;

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    private ServerSocket serverSocket;

    /**
     * method that listen ports. Creates ClientHandler for every new connection
     */
    public void listenPort() {
        try (final ServerSocket listener = new ServerSocket(10_000)) {
            System.out.println("Server started");
            serverSocket = listener;
            Socket connection = listener.accept();
            while (connection != null)
            {
                ClientHandler clientHandler = new ClientHandler(connection, messageHandler);
                clients.addClient(connection.getRemoteSocketAddress().toString(), clientHandler);

                new Thread(clientHandler).start();

                connection = listener.accept();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * method that sends message to user
     *
     * @param id      - ip adress + port
     * @param message - message body
     */
    public void sendToUser(String id, String message) {
        clients.sendMessageToClient(id, message);
    }

    /**
     * method that sends message to all users
     *
     * @param message - message body
     */
    public void sendAll(String message) {
        clients.sendMessageToAllClients(message);
    }

    /**
     * method that returns map of all users ClientHandlers
     *
     * @return ClientHandlerMap.class
     */
    public static ClientHandlerMap getClientsHandlerMap() {
        return clients;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
