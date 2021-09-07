package com.db.edu.team03.client;

import java.io.IOException;

public class ServerThread extends Thread{
    private final Connection connection;

    private static boolean IS_SERVER_WORKED = true;

    public static boolean getIsServerWorked(){
        return IS_SERVER_WORKED;
    }

    public ServerThread(Connection connection){
        this.connection = connection;
        System.out.println("Welcome to team03 chat.");
    }

    @Override
    public void run(){
        while (true) {
            try {
                System.out.println(connection.receiveMessage());
            } catch (IOException e) {
                System.out.println("Server disconnected.");
                IS_SERVER_WORKED = false;
                return;
            }
        }
    }
}
