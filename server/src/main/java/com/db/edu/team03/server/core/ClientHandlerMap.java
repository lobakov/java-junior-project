package com.db.edu.team03.server.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClientHandlerMap {
    private final Map<String, ClientHandler> clients;

    public ClientHandlerMap(){
        clients = new ConcurrentHashMap<>();
    }

    public void addClient(String adress, ClientHandler handler){
        clients.put(adress, handler);
    }

    public void removeClient(String adress){
        clients.remove(adress);
    }

    public boolean checkClientExists(String adress){
        return clients.containsKey(adress);
    }

    public void sendMessageToClient(String adress, String message){
        clients.get(adress).sendMessageToClient(message);
    }

    public void sendMessageToAllClients(String message){
        clients.forEach((k, v) -> sendMessageToClient(k, message));
    }
}
