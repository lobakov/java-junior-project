package com.db.edu.team03;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.file.FileReader;
import com.db.edu.team03.server.file.FileWriter;
import com.db.edu.team03.server.handler.*;

import java.io.File;

public class ServerApplication {
    private static final String DEFAULT_FILENAME = "chat-history.log";
    public static void main(String[] args) {
        ServerCore server = new ServerCore();
        UserHandler userHandler = new UserHandler();
        FileHandler fileHandler = new FileHandler(new File(DEFAULT_FILENAME), new FileWriter(), new FileReader());
        HistoryLogger historyLogger = new HistoryLogger(fileHandler);
        Handler messageHandler = new MessageHandler(server, userHandler, historyLogger);
        server.setHandler(messageHandler);
        server.listenPort();
    }
}
