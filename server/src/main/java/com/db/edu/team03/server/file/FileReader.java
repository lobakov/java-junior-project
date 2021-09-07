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
    private static Logger logger = LogManager.getLogger(FileReader.class);

    private BufferedReader reader;

    public FileReader(File file) {
        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(file)), "windows-1251"));
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public List<String> read() {
        List<String> historyList = new ArrayList<>();
        String readLine;

        try {
            while ((readLine = this.reader.readLine()) != null) {
                historyList.add(readLine);
            }
        } catch (IOException e) {
            logger.error(e);
        }

        return historyList;
    }
}
