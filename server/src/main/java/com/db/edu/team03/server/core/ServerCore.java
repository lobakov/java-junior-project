package com.db.edu.team03.server.core;

import com.db.edu.team03.server.handler.MessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCore {
    private MessageHandler messageHandler;
    private final ClientHandlerMap clients = new ClientHandlerMap();

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {
            System.out.println("Server started");

            for (Socket connection = listener.accept(); connection != null; connection = listener.accept()) {

                ClientHandler clientHandler = new ClientHandler(connection, messageHandler);
                clients.addClient(connection.getRemoteSocketAddress().toString(), clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendToUser(String id, String message) {
        if (clients.checkClientExists(id)){
            clients.sendMessageToClient(id, message);
        }
    }

    public void sendAll(String message) {
        clients.sendMessageToAllClients(message);
    }
}
