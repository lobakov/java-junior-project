package com.db.edu.team03.client;

import java.io.*;

public class Connection {

    private DataInputStream input;
    private DataOutputStream output;

    public Connection(DataInputStream input, DataOutputStream output) {
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
