package com.db.edu.team03.server.core;

import com.db.edu.team03.server.handler.MessageHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerCore {

    private static Logger logger = LogManager.getLogger(ServerCore.class);

    private MessageHandler messageHandler;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {
            System.out.println("Server started");

            for (Socket connection = listener.accept(); connection != null; connection = listener.accept()) {

                ClientHandler clientHandler = new ClientHandler(connection, messageHandler);
                clients.put(connection.getRemoteSocketAddress().toString(), clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    public void sendToUser(String id, String message) {
        if (clients.containsKey(id)){
            ClientHandler client = clients.get(id);

            try {
                client.getOutput().writeUTF(message);
                client.getOutput().flush();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
    }

    public void sendAll(String message) {
        clients.forEach((k, v) -> {
            try {
                v.getOutput().writeUTF(message);
                v.getOutput().flush();
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        });
    }
}
