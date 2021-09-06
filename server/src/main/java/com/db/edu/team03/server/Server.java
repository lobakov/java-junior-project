package com.db.edu.team03.server;

public interface Server {

    void sendToUser(String id, String message);
    void sendAll(String message);
}
