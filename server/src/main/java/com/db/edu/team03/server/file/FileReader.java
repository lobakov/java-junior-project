package com.db.edu.team03.server.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    private BufferedReader reader;

    public FileReader(File file) {
        try {
            this.reader = new BufferedReader(
                    new InputStreamReader(
                            new BufferedInputStream(
                                    new FileInputStream(file)), "windows-1251"));
        } catch (IOException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }

        return historyList;
    }
}
