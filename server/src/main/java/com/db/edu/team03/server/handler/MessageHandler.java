package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.core.Server;

public class MessageHandler implements Handler {

    private static final String DELIMITER = " ";
    private static final String SERVER_NAME = "Server";
    private static final String SEND = "/snd";
    private static final String CHID = "/chid";
    private static final String HIST = "/hist";
    private static final int COMMAND_INDEX = 0;
    private static final int MESSAGE_INDEX = 1;

    private final UserHandler userHandler;
    private final Server server;
    private final HistoryLogger historyLogger;


    public MessageHandler(Server server, UserHandler userHandler, HistoryLogger historyLogger) {
        this.server = server;
        this.userHandler = userHandler;
        this.historyLogger = historyLogger;
    }

    @Override
    public void accept(String id, String message) {
        userHandler.accept(id);
        parse(id, message);

    }

    private void parse(String id, String message) {
        String[] parsed = message.split(DELIMITER);
        String command = parsed[COMMAND_INDEX];
        String body = parsed[MESSAGE_INDEX];

        switch(command) {
            case SEND:
                historyLogger.saveHistory(composeMessage(id, message));
                server.sendAll(composeMessage(id, message));
                break;
            case CHID:
                userHandler.changeUsername(id, body);
                break;
            case HIST:
                server.sendToUser(id, composeMessage(SERVER_NAME, historyLogger.readHistory()));
                break;
            default:
                break;
        }
    }

    private String composeMessage(String id, String message) {
        return null;
    }
}
