package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.util.MessageFormatter;

public class MessageHandler {
    private static final String SERVER_NAME = "Server";
    private static final String WRONG_MESSAGE = "Unrecognized message";

    private static final int COMMAND_INDEX = 0;
    private static final int MESSAGE_INDEX = 1;

    private final UserHandler userHandler;
    private final ServerCore server;
    private final HistoryLogger historyLogger;
    private final MessageFormatter messageFormatter;

    public MessageHandler(ServerCore server, UserHandler userHandler, HistoryLogger historyLogger, MessageFormatter messageFormatter) {
        this.server = server;
        this.userHandler = userHandler;
        this.historyLogger = historyLogger;
        this.messageFormatter = messageFormatter;
    }

    public void accept(String address, String message) throws ServerException {
        System.out.println("id: " + address + ";  " + " message: " + message);
        userHandler.accept(address);

        if (address == null) throw new ServerException("Null user id received");
        if (message == null) throw new ServerException("Null message received");
        if (address.isEmpty()) throw new ServerException("Empty id received");
        if (message.isEmpty()) throw new ServerException("Empty message received");

        parse(address, message);
    }

    private void parse(String address, String message) {
        String[] parsed = message.split(" ");
        String command = parsed[COMMAND_INDEX];
        String body = "";

        if (parsed.length > 1) {
            body = parsed[MESSAGE_INDEX];
        }
        String id = userHandler.getIdByAddress(address);

        if (command.equals(Prefix.SEND.value)) {
            historyLogger.saveHistory(composeMessage(id, body));
            server.sendAll(composeMessage(address, body));
        } else if (command.equals(Prefix.CHID.value)) {
            userHandler.changeUsername(id, body);
        } else if (command.equals(Prefix.HIST.value)) {
            server.sendToUser(address, composeMessage(SERVER_NAME, historyLogger.readHistory()));
        } else {
            String error = composeMessage(SERVER_NAME, WRONG_MESSAGE);
            historyLogger.saveHistory(error);
            server.sendToUser(address, error);
        }
    }

    private String composeMessage(String id, String message) {
        return messageFormatter.format(id, message);
    }
}
