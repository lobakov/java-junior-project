package com.db.edu.team03.core;

import com.db.edu.team03.server.core.ClientHandler;
import com.db.edu.team03.server.handler.MessageHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClientHandlerTest {

    @Test
    public void shouldSendMessage() throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        when(socket.getOutputStream()).thenReturn(out);

        MessageHandler messageHandler = mock(MessageHandler.class);

        ClientHandler clientHandler = new ClientHandler(socket,messageHandler);

        String message = "test";
        clientHandler.sendMessageToClient(message);
        assertTrue(out.toString().contains(message));
    }

}
