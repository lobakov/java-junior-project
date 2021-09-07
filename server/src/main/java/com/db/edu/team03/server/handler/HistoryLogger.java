package com.db.edu.team03.server.handler;

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
        List<String> fileLines = fileHandler.readHistory();
        for (String line: fileLines) {
            joiner.add(line);
        }
        return joiner.toString();
    }
}
