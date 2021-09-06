package com.db.edu.team03.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleThread extends Thread{
    private final Connection connection;
    private final BufferedReader reader;

    public ConsoleThread(Connection connection){
        this.reader = new BufferedReader(new InputStreamReader(System.in));

        this.connection = connection;
    }

    @Override
    public void run() {
        try{
            while (true) {
                String message = reader.readLine();
                if (!message.isEmpty()) {
                    connection.sendMessage(message); // protocol
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
