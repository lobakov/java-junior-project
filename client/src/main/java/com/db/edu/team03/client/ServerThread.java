package com.db.edu.team03.client;

import java.io.IOException;

public class ServerThread extends Thread{
    private final Connection connection;

    private static boolean isServerWorked = true;

    public static boolean getIsServerWorked(){
        return isServerWorked;
    }

    public ServerThread(Connection connection){
        this.connection = connection;
        System.out.println("Welcome to team03 chat.");
        printInvitation();
    }

    @Override
    public void run(){
        while (!this.isInterrupted()) {
            printMessage();
        }
    }

    void printMessage() {
        try {
            System.out.println(connection.receiveMessage());
            printInvitation();
        } catch (IOException e) {
            System.out.println("Server disconnected.");
            isServerWorked = false;
            return;
        }
    }
    private void printInvitation() {
        System.out.print("> ");
    }
}
