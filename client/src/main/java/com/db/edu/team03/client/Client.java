package com.db.edu.team03.client;

import com.db.edu.team03.client.exception.ClientException;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 10_000;

    public void start() throws ClientException {
        try (Socket socket = new Socket(HOST, PORT);
        DataInputStream dis = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            Connection connection = new Connection(dis, dos);

            ServerThread serverThread = new ServerThread(connection);
            serverThread.start();
            serverThread.setDaemon(true);

            ConsoleThread consoleThread = new ConsoleThread(connection);
            consoleThread.start();
            consoleThread.setDaemon(true);

            serverThread.join();
            consoleThread.join();

        } catch (IOException | InterruptedException e) {
            throw new ClientException();
        }
    }
}
