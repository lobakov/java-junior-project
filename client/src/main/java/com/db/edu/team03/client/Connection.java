package com.db.edu.team03.client;

import java.io.*;
import java.net.Socket;

public class Connection {

    private DataInputStream input;
    private DataOutputStream output;

    public Connection(Socket socket, DataInputStream input, DataOutputStream output) throws IOException {
        final Socket connection = socket;
        this.input = input;
        this.output = output;
    }

    public void sendMessage(String message) throws IOException {
        output.writeUTF(message);
        output.flush();
    }

    public String receiveMessage() throws IOException {
        return input.readUTF();
    }
}
