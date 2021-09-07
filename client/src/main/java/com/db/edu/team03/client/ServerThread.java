package com.db.edu.team03.client;

import java.io.IOException;

public class ServerThread extends Thread{
    private final Connection connection;

    public ServerThread(Connection connection){
        this.connection = connection;
        System.out.println("Welcome to team03 chat.");
        printInvitation();
    }

    @Override
    public void run(){
        while (true) {
            try {
                System.out.println(connection.receiveMessage());
                printInvitation();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void printInvitation() {
        System.out.print("> ");
    }
}
