package com.db.edu.team03.client;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
public class ConsoleThreadTest {

    private final Connection connectionMock = mock(Connection.class);
    private final ConsoleThread thread = new ConsoleThread(connectionMock);

    @Test
    public void shouldNotSendEmptyMessage() throws IOException {
        assertFalse(thread.send(""));
    }

    @Test
    public void shouldNotSendBigMessage() throws IOException {
        String message = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
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
}
