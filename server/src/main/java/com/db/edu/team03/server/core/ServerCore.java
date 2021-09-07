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

    String messageToAllClients ="";
    boolean haveSmthToWriteToAllClients = false;

    String messageToClient = "";
    boolean haveSmthToWriteToClient = false;
    String clientIdNeededForMessage = "";

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
        clientIdNeededForMessage = id;
        messageToClient = message;
        haveSmthToWriteToClient = true;
    }

    @Override
    public void sendAll(String message) {
        haveSmthToWrite = true;
        outputMessage = message;
        haveSmthToWriteToAllClients =true;
        messageToAllClients = message;
    }

    private class ClientHandler implements Runnable {
        private final Socket socket;
        private final Map<String, ClientHandler> clients;
        private DataOutputStream output;

        private String clientId;

        public ClientHandler(Socket socket, Map<String, ClientHandler> clients)
                throws IOException {
            this.socket = socket;
            this.clients = clients;
            this.output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        }
        @Override
        public void run() {
            try (
                    final DataInputStream input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            ) {

                String IpAddress = socket.getRemoteSocketAddress().toString();
                String clientId = IpAddress;
                clients.put(clientId, this);
                while (true) {
                    final String message = input.readUTF();
                    handler.accept(IpAddress,message);
                    if (haveSmthToWriteToAllClients) {
                        clients.forEach((k, v) -> {
                            try {
                                v.output.writeUTF(messageToAllClients);
                                v.output.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        haveSmthToWriteToAllClients =false;
                    }
                    if (haveSmthToWriteToClient){
                        if (clients.containsKey(clientIdNeededForMessage)){
                            ClientHandler client = clients.get(clientIdNeededForMessage);
                            client.output.writeUTF(messageToClient);
                            client.output.flush();
                            haveSmthToWriteToClient = false;
                        }
                    }
                }

            } catch (IOException | ServerException e) {
                e.printStackTrace(System.err);
            }
        }
    }
}
