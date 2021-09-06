package com.db.edu.team03.server.file;

import java.io.*;

public class FileWriter {
    private BufferedWriter writer;

    public FileWriter(File file) {
        try {
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new BufferedOutputStream(
                                    new FileOutputStream(file, true)), "windows-1251"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        try {
            writer.append(message).append(System.lineSeparator());
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
