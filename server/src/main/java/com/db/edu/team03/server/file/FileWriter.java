package com.db.edu.team03.server.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

/**
 * FileWriter handles low-level logic of writing string messages to chat history log.
 */
public class FileWriter {

    private static Logger logger = LogManager.getLogger(FileWriter.class);
    private BufferedWriter writer;

    public FileWriter(File file) {
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(file, true)), "windows-1251"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public void write(String message) {
        try {
            writer.append(message).append(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
