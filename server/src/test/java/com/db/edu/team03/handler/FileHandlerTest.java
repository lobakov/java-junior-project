package com.db.edu.team03.handler;

import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;
import com.db.edu.team03.server.handler.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


public class FileHandlerTest {
    private static FileReader fileReader;
    private static FileWriter fileWriter;
    private static FileHandler fileHandler;

    @BeforeEach
    public void setUp() {
        fileReader = mock(FileReader.class);
        fileWriter = mock(FileWriter.class);

        fileHandler = new FileHandler(fileReader, fileWriter);
    }

    @Test
    public void shouldCallReadMethod() {
        fileHandler.readHistory();

        verify(fileReader, times(1)).read();
    }

    @Test
    public void shouldCallWriteMethod() {
        String message = "message";

        fileHandler.write(message);

        verify(fileWriter, times(1)).write(eq(message));
    }
}
