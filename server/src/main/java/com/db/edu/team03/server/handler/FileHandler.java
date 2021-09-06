package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.File.FileReader;
import com.db.edu.team03.server.File.FileWriter;

import java.io.File;
import java.util.List;

public class FileHandler {

    private final FileReader reader;
    private final FileWriter writer;
    private File file;

    public FileHandler(File file, FileWriter writer, FileReader reader) {
        this.writer = writer;
        this.reader = reader;
        this.file = file;
    }

    public List<String> readHistory() {
        return reader.read(file);
    }

    public void write(String message) {
        writer.write(file, message);
    }
}
