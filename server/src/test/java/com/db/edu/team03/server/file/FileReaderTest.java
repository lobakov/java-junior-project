package com.db.edu.team03.server.file;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {
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
    public void shouldReadEmptyFile() {
        FileReader reader = new FileReader(file);

        assertEquals("There are no messages in history", reader.read().get(0));
    }

    @Test
    public void shouldReadNonEmptyFile() {
        String writedString = "something";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(writedString);
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileReader reader = new FileReader(file);

        assertTrue(reader.read().contains(writedString));
    }

    @Test
    public void shouldGetErrorWhenNoFile() {
        String writedString = "something";
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(file), "utf-8"))) {
            writer.write(writedString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        file.delete();
        FileReader reader = new FileReader(file);
        assertTrue(reader.read().contains("Couldn't get history"));
    }
}
