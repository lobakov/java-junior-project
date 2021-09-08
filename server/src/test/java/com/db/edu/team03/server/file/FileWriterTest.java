package com.db.edu.team03.server.file;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterTest {
    private final String FILE_PATH = "TestLog.log";
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private File file;
    private final PrintStream standardOut = System.out;
    @BeforeEach
    void setup() throws IOException {
        file = new File(FILE_PATH);
        file.createNewFile();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
        file.delete();
    }

    @Test
    public void shouldWrite() throws IOException {
        String writedString = "something";

        FileWriter writer = new FileWriter(file);

        writer.write(writedString);

        assertEquals(new String(Files.readAllBytes(Paths.get(FILE_PATH))), writedString + System.lineSeparator());
    }

    @Test
    public void shouldGetErrorWhenTroublesWithFile() {
        String writedString = "something";

        FileWriter writer = new FileWriter(file);

        writer.write(writedString);
        file.setReadOnly();
        writer.write(writedString);
        assertEquals("Couldn't save history", outputStreamCaptor.toString().trim());
        file.delete();
    }

}
