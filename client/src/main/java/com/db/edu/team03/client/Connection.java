package com.db.edu.team03.client;

import java.io.*;
import java.net.Socket;

public class Connection {
    private DataInputStream input;
    private DataOutputStream output;

    public Connection(Socket socket) throws IOException {
        final Socket connection = socket;
        this.input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
        this.output = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()));
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(message); // protocol
        output.flush();
    }

    public String receiveMessage() throws IOException {
        return input.readUTF();
    }
}
