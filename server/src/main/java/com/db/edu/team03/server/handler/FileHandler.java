package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;

import java.io.File;
import java.util.List;

/**
 * FileHandler handles log file of chat history, by writing to or reading from file,
 * which location is configured in ServerApplication class.
 */
public class FileHandler {

    private final FileReader reader;
    private final FileWriter writer;

    public FileHandler(File file) {
        this.writer = new FileWriter(file);
        this.reader = new FileReader(file);
    }

    public List<String> readHistory() {
        return reader.read();
    }

    public void write(String message) {
        writer.write(message);
    }

    public FileReader getReader() {
        return reader;
    }

    public FileWriter getWriter() {
        return writer;
    }
}
