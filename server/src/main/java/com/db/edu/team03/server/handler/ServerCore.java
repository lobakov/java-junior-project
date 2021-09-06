package com.db.edu.team03.server.handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerCore {
    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {

            try (
                    final Socket connection = listener.accept();
                    final DataInputStream input = new DataInputStream(new BufferedInputStream(connection.getInputStream()));
                    final DataOutputStream output = new DataOutputStream(new BufferedOutputStream(connection.getOutputStream()))
            ) {
                final Handler handler = new MessageHandler();
                String IpAddress = connection.getRemoteSocketAddress().toString();
                String port = Integer.toString(connection.getPort());
                String address = IpAddress+":"+port;
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
}
