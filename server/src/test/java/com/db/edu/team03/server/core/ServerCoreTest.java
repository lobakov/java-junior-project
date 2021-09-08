package com.db.edu.team03.server.core;

import com.db.edu.team03.SysoutCaptureAndAssertionAbility;
import com.db.edu.team03.server.handler.MessageHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServerCoreTest {
    private ServerCore serverCore;

    @BeforeEach
    public void setUp() {
        serverCore = new ServerCore();
        serverCore.setMessageHandler(mock(MessageHandler.class));
    }

    @AfterEach
    public void tearDown() {
        ServerCore.getClientsHandlerMap().clear();
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

    @Test
    public void shouldCreateNewClient() throws IOException {
        Socket connection = mock(Socket.class);
        SocketAddress address = new InetSocketAddress("localhost", 8080);
        when(connection.getRemoteSocketAddress()).thenReturn(address);
        serverCore.createNewClient(connection);
        assertTrue(ServerCore.getClientsHandlerMap().isClientExists(address.toString()));
    }

    @Test
    public void shouldStartNewThreadWithNewClient() throws IOException {
        Thread t = mock(Thread.class);

        serverCore.startClientHandlerThread(t);
        verify(t).start();
    }


}
