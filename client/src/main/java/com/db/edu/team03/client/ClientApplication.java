package com.db.edu.team03.client;

import com.db.edu.team03.client.exception.ClientException;

public class ClientApplication {
    public static void main(String[] args) {
        try {
            new Client().start();
        } catch (ClientException e) {
            System.out.println("Client error. Shut down.");
        }
    }
}
