package com.db.edu.team03.server.handler;

import java.util.List;
import java.util.StringJoiner;

/**
 * HistoryLogger class that handles chat history by saving to or reading from file,
 * using FileHandler class as low-level helper.
 */
public class HistoryLogger {

    private static final String DELIMITER = System.lineSeparator();

    private final FileHandler fileHandler;

    public HistoryLogger(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
    }

    /**
     * Saves message to chat history log
     *
     * @param message - message, received by user
     */
    public void saveHistory(String message) {
        fileHandler.write(message);
    }

    /**
     * Reads saved history from chat history log
     *
     * @return - composed log of all messages that present in chat history log
     */
    public String readHistory() {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        List<String> fileLines = fileHandler.readHistory();
        joiner.add("=========");
        for (String line : fileLines) {
            joiner.add(line);
        }
        joiner.add("=========");
        return joiner.toString();
    }
}
