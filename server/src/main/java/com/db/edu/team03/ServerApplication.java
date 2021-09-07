package com.db.edu.team03;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.handler.*;
import com.db.edu.team03.server.util.MessageFormatter;

import java.io.File;

public class ServerApplication {
    private static final String HOME = System.getProperty("user.home");
    private static final String DEFAULT_FILENAME = HOME + "/team03-chat-history.log";

    public static void main(String[] args) {
        ServerCore server = new ServerCore();
        UserHandler userHandler = new UserHandler();
        FileHandler fileHandler = new FileHandler(new File(DEFAULT_FILENAME));
        HistoryLogger historyLogger = new HistoryLogger(fileHandler);
        MessageFormatter messageFormatter = new MessageFormatter();
        MessageHandler messageHandler = new MessageHandler(server, userHandler, historyLogger, messageFormatter);
        server.setHandler(messageHandler);
        server.listenPort();
    }
}
