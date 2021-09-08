package com.db.edu.team03.client;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServerThreadTest {

    private Connection connection = mock(Connection.class);
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
    void shouldReturnFalseWhenServerThreadNotCreated() {
        assertFalse(ServerThread.getIsServerWorked());
    }

    @Test
    void shouldReceiveMessages() throws IOException, InterruptedException {
        ServerThread serverThread = new ServerThread(connection);
        when(connection.receiveMessage()).thenReturn(message);
        serverThread.printMessage();
        assertEquals("Welcome to team03 chat." + System.lineSeparator() + "> " + expected, outputStreamCaptor.toString().trim());
    }

    @Test
    void shouldDisableServerThreadWhenErrorInCOnnection() throws IOException, InterruptedException {
        ServerThread serverThread = new ServerThread(connection);
        when(connection.receiveMessage()).thenThrow(IOException.class);
        serverThread.printMessage();
        assertFalse(ServerThread.getIsServerWorked());
    }

    @Test
    void shouldPrintServerDisconnectedMessageWhenTroublesWithConnection() throws IOException {
        ServerThread serverThread = new ServerThread(connection);
        when(connection.receiveMessage()).thenThrow(IOException.class);
        serverThread.printMessage();
        assertEquals("Welcome to team03 chat." + System.lineSeparator() + "> Server disconnected.", outputStreamCaptor.toString().trim());
    }

    @Test
    void should() {
        ServerThread serverThread = mock(ServerThread.class);

    }

    @Test
    void shouldCatchIOException() throws IOException {
        when(connection.receiveMessage()).thenThrow(IOException.class);

        serverThread.printMessage();

        assertEquals("Server disconnected.", outputStreamCaptor.toString().trim());
    }

    @Test
    void shouldPrintIntroMessage() {
        ServerThread serverThreadTest = new ServerThread(connection);
        assertEquals("Welcome to team03 chat." + System.lineSeparator() + ">", outputStreamCaptor.toString().trim());
    }
}
