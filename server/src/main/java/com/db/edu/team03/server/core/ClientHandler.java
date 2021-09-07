package com.db.edu.team03.server.core;

import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.MessageHandler;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private final Socket socket;
    private DataOutputStream output;
    private final MessageHandler handler;

    public ClientHandler(Socket socket, MessageHandler handler) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.handler = handler;
    }

    public DataOutputStream getOutput(){
        return output;
    }

    @Override
    public void run() {
        try (
                final DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        ) {
            String IpAddress = socket.getRemoteSocketAddress().toString();

            while (true) {
                final String message = input.readUTF();
                handler.accept(IpAddress,message);
            }

        } catch (IOException | ServerException e) {
            e.printStackTrace(System.err);
        }
    }
}
