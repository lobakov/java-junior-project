package com.db.edu.team03.client;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ConsoleThreadTest {

    private final Connection connectionMock = mock(Connection.class);
    private final ConsoleThread thread = new ConsoleThread(connectionMock);

    @Test
    public void shouldNotSendEmptyMessage() throws IOException {
        assertFalse(thread.send(""));
    }

    @Test
    public void shouldNotSendBigMessage() throws IOException {
        String message = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String outputMessage = "Your message longer than 150 symbols. Please, send shorter message.";
        ByteArrayOutputStream OUT = new ByteArrayOutputStream();
        OUT.reset();
        System.setOut(new PrintStream(OUT));
        thread.send(message);
        assertTrue(OUT.toString().contains(outputMessage));
    }

    @Test
    public void shouldSendMessage() throws IOException {
        String message = "good message";
        assertTrue(thread.send(message));
    }

    @Test
    public void shouldSend150LengthMessage() throws IOException {
        String message = "123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
        assertTrue(thread.send(message));
    }
}
