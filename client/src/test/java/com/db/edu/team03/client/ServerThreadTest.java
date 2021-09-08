package com.db.edu.team03.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ServerThreadTest {

    private Connection connection = mock(Connection.class);
    private ServerThread serverThread = new ServerThread(connection);
    private final String message = "test";
    private final String expected = message + System.lineSeparator() + ">";
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void reset() {
        System.setOut(standardOut);
    }

    @Test
    void shouldReceiveMessages() throws IOException, InterruptedException {
        when(connection.receiveMessage()).thenReturn(message);

        serverThread.printMessage();

        assertEquals(expected, outputStreamCaptor.toString().trim());
    }
}
