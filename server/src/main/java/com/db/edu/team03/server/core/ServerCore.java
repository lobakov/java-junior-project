package com.db.edu.team03.server.core;

import com.db.edu.team03.server.exception.PortListeningException;
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

    /**
     * method that listen ports. Creates ClientHandler for every new connection
     */
    public void listenPort(int port) throws PortListeningException {
        try (final ServerSocket listener = new ServerSocket(port)) {
            Socket connection = listener.accept();
            while (connection != null)
            {
                ClientHandler handler = createNewClient(connection);
                startClientHandlerThread(new Thread(handler));
                connection = listener.accept();
            }
        } catch (IllegalArgumentException | IOException e) {
            throw new PortListeningException();
        }
    }

    ClientHandler createNewClient(Socket connection) throws IOException {
        ClientHandler clientHandler = new ClientHandler(connection, messageHandler);
        clients.addClient(connection.getRemoteSocketAddress().toString(), clientHandler);
        return clientHandler;
    }

    void startClientHandlerThread(Thread t) {
        t.start();
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
}
