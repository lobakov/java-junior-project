package com.db.edu.team03.server.file;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileReader handles low-level logic of reading the whole file and present it as a list of string lines.
 */
public class FileReader {

    private BufferedReader reader;
    private File file;

    public FileReader(File file) {
        this.file = file;
    }

    public List<String> read() {
        List<String> historyList = new ArrayList<>();
        String readLine;

        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(file)), "windows-1251"));
            while ((readLine = this.reader.readLine()) != null) {
                historyList.add(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return historyList;
    }
}
