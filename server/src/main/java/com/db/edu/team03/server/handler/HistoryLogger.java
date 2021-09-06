package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.File.FileReader;
import com.db.edu.team03.server.File.FileWriter;

import java.io.File;
import java.util.List;
import java.util.StringJoiner;

public class HistoryLogger {

    private static final String DELIMITER = System.lineSeparator();

    private final FileHandler fileHandler;

    public HistoryLogger(FileHandler fileHandler) {
        this.fileHandler = fileHandler;

    }

    public void saveHistory(String message) {
        fileHandler.write(message);
    }

    public String readHistory() {
        return linesToString();
    }

    private String linesToString() {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        List<String> fileLines = reader.read(file);
        for (String line: fileLines) {
            joiner.add(line);
        }
        return joiner.toString();
    }
}
