package com.db.edu.team03.handler;

import com.db.edu.team03.server.handler.FileHandler;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

@Disabled
public class FileHandlerTest {
    File file = mock(File.class);
    FileHandler fileHandler = new FileHandler(file);

    @Test
    public void shouldReadHistoryFromFile() {
    }
}
