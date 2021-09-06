package com.db.edu.util.file;

import java.io.*;

public class FileWriter implements Writer {
    private final String fileName;
    private final int bufferSize;
    private final String charSet;

    public FileWriter(String fileName, int bufferSize, String charSet) {
        this.fileName = fileName;
        this.bufferSize = bufferSize;
        this.charSet = charSet;
    }

    @Override
    public synchronized void write(Object data) {
//        try {
//            if (message == null) throw new NullPointerException();
//        } catch (NullPointerException npe) {
//            throw new SaveException("Empty message: " + npe.getMessage(), npe);
//        }

        try (BufferedWriter fileWriter = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(fileName, true), charSet), bufferSize)) {
            fileWriter.write(data.toString());
        } catch (FileNotFoundException fnfe) {
           // throw new SaveException("Log to file " + this.fileName + " failed", fnfe);
        } catch (IOException ioe) {
           // throw new SaveException("Log to file " + this.fileName + " failed", ioe);
        }
    }
}
