package com.db.edu.team03.server.file;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileWriterTest {
    private final String FILE_PATH = "TestLog.log";

    private File file;

    @BeforeEach
    void setup() throws IOException {
        file = new File(FILE_PATH);
        file.createNewFile();
    }

    @AfterEach
    void tearDown() {
        file.delete();
    }

    @Test
    public void shouldWrite() throws IOException {
        String writedString = "something";

        FileWriter writer = new FileWriter(file);

        writer.write(writedString);

        Assertions.assertEquals(new String(Files.readAllBytes(Paths.get(FILE_PATH))), writedString + System.lineSeparator());
    }
}
