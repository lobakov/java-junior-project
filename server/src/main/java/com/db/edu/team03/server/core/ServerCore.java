package com.db.edu.team03.server.core;

import com.db.edu.team03.server.handler.Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCore implements Server {

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {

            try (
                    final Socket connection = listener.accept();
                    final DataInputStream input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
                    final DataOutputStream output = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()))
            ) {

                String IpAddress = connection.getRemoteSocketAddress().toString();
                String address = IpAddress;
                while (true) {
                    final String message = input.readUTF();
                    handler.accept(address,message);
                }

            } catch (IOException e) {
                e.printStackTrace(System.err);
            }

        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void sendToUser(String id, String message) {

    }

    @Override
    public void sendAll(String message) {

    }
}
