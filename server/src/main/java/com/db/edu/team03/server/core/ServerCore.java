package com.db.edu.team03.server.core;

import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.handler.Handler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerCore implements Server {

    private Handler handler;

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void listenPort(){
        try (final ServerSocket listener = new ServerSocket(10_000)) {

            final Map<String, ClientHandler> clients = new ConcurrentHashMap<String, ClientHandler>();

            for (Socket connection = listener.accept(); connection != null; connection = listener.accept()) {

                Runnable handler = new ClientHandler(connection, clients);
                new Thread(handler).start();
            }
        }catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void sendToUser(String id, String message) {

    }

    @Override
    public void sendAll(String message) {

    }

    private class ClientHandler implements Runnable {
        private final Socket socket;
        private final Map<String, ClientHandler> clients;

        private String clientId;

        public ClientHandler(Socket socket, Map<String, ClientHandler> clients)
                throws IOException {
            this.socket = socket;
            this.clients = clients;
        }
        @Override
        public void run() {
            try (
                    //final Socket connection = listener.accept();
                    final DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    final DataOutputStream output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))
            ) {

                String IpAddress = socket.getRemoteSocketAddress().toString();
                String address = IpAddress;
                while (true) {
                    final String message = input.readUTF();
                    handler.accept(address,message);
                }

            } catch (IOException | ServerException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
