package com.db.edu.team03.client;

import java.io.*;
import java.net.Socket;

public class Client {

    private static final String HOST = "localhost";
    private static final int PORT = 10_000;

    public static void main(String[] args) {
        Socket socket;
        DataOutputStream dos;
        DataInputStream dis;

        try {
            socket = new Socket(HOST, PORT);
            dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            Connection connection = new Connection(socket, dis, dos);

            ServerThread serverThread = new ServerThread(connection);
            serverThread.start();

            ConsoleThread consoleThread = new ConsoleThread(connection);
            consoleThread.start();

            serverThread.join();
            consoleThread.join();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (InterruptedException e) {
            System.out.println("Client terminated.");
        }
    }
}
