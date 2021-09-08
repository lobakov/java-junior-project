package com.db.edu.team03;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.PortListeningException;
import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;
import com.db.edu.team03.server.handler.*;
import com.db.edu.team03.server.util.MessageFormatter;

import java.io.File;

public class ServerApplication {
    private static final String HOME = System.getProperty("user.home");
    private static final String DEFAULT_FILENAME = HOME + "/team03-chat-history.log";

    static File createFile(String fileName) {
        return new File(fileName);
    }

    static FileReader createFileReader(File file){
        return new FileReader(file);
    }

    static FileWriter createFileWriter(File file){
        return new FileWriter(file);
    }

    static FileHandler createFileHandler(FileReader reader, FileWriter writer){
        return new FileHandler(reader, writer);
    }

    public static void main(String[] args) {
        ServerCore server = new ServerCore();

        File file = createFile(DEFAULT_FILENAME);

        FileReader fileReader = createFileReader(file);
        FileWriter fileWriter = createFileWriter(file);

        FileHandler fileHandler = createFileHandler(fileReader, fileWriter);

        HistoryLogger historyLogger = new HistoryLogger(fileHandler);
        UserHandler userHandler = new UserHandler();
        MessageFormatter messageFormatter = new MessageFormatter();

        MessageHandler messageHandler = new MessageHandler(server, historyLogger, userHandler, messageFormatter);
        server.setMessageHandler(messageHandler);
        try {
            server.listenPort(10000);
        } catch (PortListeningException e) {
            System.out.println("Exception while listening port.");
        }
    }
}
