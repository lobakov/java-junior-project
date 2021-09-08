package com.db.edu.team03.server.file;

import org.junit.jupiter.api.*;

import java.io.*;
import java.util.ArrayList;

@Disabled
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

        Assertions.assertEquals(new ArrayList<String>(), reader.read());
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

        Assertions.assertTrue(reader.read().contains(writedString));
    }
}
