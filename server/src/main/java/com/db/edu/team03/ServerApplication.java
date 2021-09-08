package com.db.edu.team03;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;
import com.db.edu.team03.server.handler.*;
import com.db.edu.team03.server.util.MessageFormatter;

import java.io.File;

public class ServerApplication {
    private static final String HOME = System.getProperty("user.home");
    private static final String DEFAULT_FILENAME = HOME + "/team03-chat-history.log";

    public static void main(String[] args) {
        ServerCore server = new ServerCore();

        File file = new File(DEFAULT_FILENAME);

        FileReader fileReader = new FileReader(file);
        FileWriter fileWriter = new FileWriter(file);

        FileHandler fileHandler = new FileHandler(fileReader, fileWriter);

        HistoryLogger historyLogger = new HistoryLogger(fileHandler);
        UserHandler userHandler = new UserHandler();
        MessageFormatter messageFormatter = new MessageFormatter();

        MessageHandler messageHandler = new MessageHandler(server, historyLogger, userHandler, messageFormatter);
        server.setMessageHandler(messageHandler);
        server.listenPort();
    }
}
