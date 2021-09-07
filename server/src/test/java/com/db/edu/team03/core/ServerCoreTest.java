package com.db.edu.team03.core;

import com.db.edu.team03.server.core.ClientHandler;
import com.db.edu.team03.server.core.ClientHandlerMap;
import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.handler.MessageHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServerCoreTest {
    private ServerCore serverCore;

    @BeforeEach
    public void setUp() {
        serverCore = new ServerCore();
        serverCore.setMessageHandler(mock(MessageHandler.class));
    }

    @Test
    public void sendAllShouldCallSendMessageToClientMethod() {
        ClientHandler handler = mock(ClientHandler.class);

        ClientHandlerMap clients = ServerCore.getClientsHandlerMap();
        clients.addClient("", handler);

        serverCore.sendAll("");
        verify(handler, times(1)).sendMessageToClient(any());
    }

    @Test
    public void sendToClientShouldSendMessage() {
        ClientHandler handler = mock(ClientHandler.class);
        String clientAdress = "/127.0.0.1:60158:";

        ClientHandlerMap clients = ServerCore.getClientsHandlerMap();
        clients.addClient(clientAdress, handler);

        serverCore.sendToUser(clientAdress, "");
        verify(handler, times(1)).sendMessageToClient(any());
    }

    @Test
    public void sendToClientWhickNoExistsShouldNotSendMessage() {
        ClientHandler handler = mock(ClientHandler.class);

        ClientHandlerMap clients = ServerCore.getClientsHandlerMap();
        clients.addClient("/127.0.0.1:60158:", handler);

        serverCore.sendToUser("/127.0.0.1:99999:", "");
        verify(handler, times(0)).sendMessageToClient(any());
    }
}
