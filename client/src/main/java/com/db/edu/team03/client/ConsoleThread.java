package com.db.edu.team03.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleThread extends Thread {
    private final Connection connection;
    private final BufferedReader reader;
    private static final int MAX_MESSAGE_LENGTH = 150;

    public ConsoleThread(Connection connection) {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
        this.connection = connection;
    }

    @Override
    public void run() {
        try{
            while (ServerThread.getIsServerWorked()) {
                String message = reader.readLine();
                if (ServerThread.getIsServerWorked()) {
                    send(message);
                } else {
                    System.out.println("Server unavailable, cannot send messages. Exiting.");
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean send(String message) throws IOException {
        boolean res = false;
        if (!message.isEmpty() && message.length() <= MAX_MESSAGE_LENGTH) {
            connection.sendMessage(message); // protocol
            res = true;
        } else if (message.length() > MAX_MESSAGE_LENGTH){
            System.out.println("Your message longer than 150 symbols. Please, send shorter message.");
        }
        return res;
    }
}
