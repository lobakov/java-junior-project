package com.db.edu.team03.server.core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ServerCore - class that contains map of <Client adress, ClientHandler> and public API for it
 */
public class ClientHandlerMap {
    private final Map<String, ClientHandler> clients;
    private Object monitor = new Object();

    public ClientHandlerMap() {
        clients = new HashMap<>();
    }

    public void addClient(String adress, ClientHandler handler) {
        synchronized(monitor){
            clients.put(adress, handler);
        }
    }

    public void removeClient(String adress) {
        synchronized(monitor) {
            clients.remove(adress);
        }
    }

    public boolean checkClientExists(String adress) {
        return clients.containsKey(adress);
    }

    public void sendMessageToClient(String adress, String message) {
        synchronized(monitor) {
            if (checkClientExists(adress)) {
                clients.get(adress).sendMessageToClient(message);
            }
        }
    }

    public void sendMessageToAllClients(String message) {
        synchronized(monitor) {
            clients.forEach((k, v) -> sendMessageToClient(k, message));
        }
    }
}
