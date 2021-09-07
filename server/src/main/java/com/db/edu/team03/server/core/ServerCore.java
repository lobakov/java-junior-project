package com.db.edu.team03.server.core;

import com.db.edu.team03.server.handler.MessageHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerCore {

    private MessageHandler handler;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public void setHandler(MessageHandler handler) {
        this.handler = handler;
    }

    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {
            for (Socket connection = listener.accept(); connection != null; connection = listener.accept()) {

                ClientHandler clientHandler = new ClientHandler(connection, handler);
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
                e.printStackTrace();
            }
        }
    }

    public void sendAll(String message) {
        clients.forEach((k, v) -> {
            try {
                v.getOutput().writeUTF(message);
                v.getOutput().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
