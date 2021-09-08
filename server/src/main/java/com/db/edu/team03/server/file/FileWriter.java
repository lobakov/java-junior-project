package com.db.edu.team03.server.file;

import java.io.*;

/**
 * FileWriter handles low-level logic of writing string messages to chat history log.
 */
public class FileWriter {
    private BufferedWriter writer;
    private File file;

    public FileWriter(File file) {
        this.file = file;
    }

    public synchronized void write(String message) {
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(file, true)), "windows-1251"));
            writer.append(message).append(System.lineSeparator());
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
