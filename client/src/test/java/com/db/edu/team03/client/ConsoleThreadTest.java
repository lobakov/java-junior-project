package com.db.edu.team03.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.mock;
public class ConsoleThreadTest {
    @Test
    public void shouldNotSendEmptyMessage() throws IOException {
        Connection connectionMock = mock(Connection.class);
        ConsoleThread thread = new ConsoleThread(connectionMock);
        Assertions.assertFalse(thread.send(""));
    }

    @Test
    public void shouldNotSendBigMessage() throws IOException {
        Connection connectionMock = mock(Connection.class);
        ConsoleThread thread = new ConsoleThread(connectionMock);
        String message = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        Assertions.assertFalse(thread.send(message));
    }

    @Test
    public void shouldSendMessage() throws IOException {
        Connection connectionMock = mock(Connection.class);
        ConsoleThread thread = new ConsoleThread(connectionMock);
        String message = "good message";
        Assertions.assertTrue(thread.send(message));
    }
}
