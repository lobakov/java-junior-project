package com.db.edu.team03.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ConnectionTest {

    private DataOutputStream out;
    private DataInputStream in;
    private Socket socket;
    private Connection connection;
    private final String message = "test";

    @BeforeEach
    void setUp() throws IOException {
        socket = mock(Socket.class);
        out = mock(DataOutputStream.class);
        in = mock(DataInputStream.class);
        connection = new Connection(socket, in, out);
    }

    @Test
    void shouldSendMessageWhenMessageProvided() throws IOException {
        when(socket.getOutputStream()).thenReturn(out);
        Mockito.doNothing().when(out).writeUTF(message);
        Mockito.doNothing().when(out).flush();

        connection.sendMessage(message);

        verify(out).writeUTF(message);
        verify(out).flush();
    }
}
