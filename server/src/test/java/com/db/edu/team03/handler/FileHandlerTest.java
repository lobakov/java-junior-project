package com.db.edu.team03.handler;

import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;
import com.db.edu.team03.server.handler.FileHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.File;

@Disabled
public class FileHandlerTest {
    private static File file = mock(File.class);

    private static  FileReader fileReader = new FileReader(file);
    private static FileWriter fileWriter = new FileWriter(file);
    private static FileHandler fileHandler = new FileHandler(fileReader, fileWriter);

    @Test
    public void shouldReadHistoryFromFile() {
    }
}
