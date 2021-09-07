package com.db.edu.team03.client;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try {
            Connection connection = new Connection(new Socket("localhost", 10_000));

            ServerThread serverThread = new ServerThread(connection);
            serverThread.start();

            ConsoleThread consoleThread = new ConsoleThread(connection);
            consoleThread.start();

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }
}
