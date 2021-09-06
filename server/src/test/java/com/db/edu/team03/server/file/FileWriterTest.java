package com.db.edu.team03.server.file;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class FileWriterTest {
    private File file;

    @BeforeEach
    void setup() throws IOException {
        file = new File("TestLog.log");
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
        InputStream inputStream = new FileInputStream(file);

        writer.write(writedString);

        Assertions.assertEquals(new String(inputStream.readAllBytes()), writedString + System.lineSeparator());
    }
}
