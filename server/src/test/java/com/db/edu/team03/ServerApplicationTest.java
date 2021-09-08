package com.db.edu.team03;

import com.db.edu.team03.server.handler.MessageHandler;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerApplicationTest {
    @Test
    public void shouldCreateCorrectFile(){
        assertNotNull(ServerApplication.createFile("file.txt"));
    }

    @Test
    public void shouldThrowExceptionWhenFileNameIsNull(){
        assertThrows(NullPointerException.class,() -> ServerApplication.createFile(null));
    }

    @Test
    public void shouldCreateCorrectFileReader(){
        assertNotNull(ServerApplication.createFileReader(ServerApplication.createFile("file.txt")));
    }

    @Test
    public void shouldThrowExceptionWhenFileIsNullWhenCreateFileReader(){
        assertThrows(IllegalArgumentException.class,() -> ServerApplication.createFileReader(null));
    }

    @Test
    public void shouldCreateCorrectFileWriter(){
        assertNotNull(ServerApplication.createFileWriter(ServerApplication.createFile("file.txt")));
    }

    @Test
    public void shouldThrowExceptionWhenFileIsNullWhenCreateFileWriter(){
        assertThrows(IllegalArgumentException.class,() -> ServerApplication.createFileWriter(null));
    }

    @Test
    public void shouldCreateCorrectFileHandler(){
        File file = ServerApplication.createFile("file.txt");
        assertNotNull(ServerApplication.createFileHandler(ServerApplication.createFileReader(file), ServerApplication.createFileWriter(file)));
    }

    @Test
    public void shouldThrowExceptionWhenFileReaderIsNull(){
        File file = ServerApplication.createFile("file.txt");
        assertThrows(IllegalArgumentException.class,() -> ServerApplication.createFileHandler(null, ServerApplication.createFileWriter(file)));
    }

    @Test
    public void shouldThrowExceptionWhenFileWriterIsNull(){
        File file = ServerApplication.createFile("file.txt");
        assertThrows(IllegalArgumentException.class,() -> ServerApplication.createFileHandler(ServerApplication.createFileReader(file), null));
    }
}
