package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import com.db.edu.team03.server.util.MessageFormatter;

import java.util.StringJoiner;

/**
 * MessageHandler - class that parses incoming messages and delegates further processing to another classes.
 * To delegate processing to classes MessageHandler contains primary processing logic and basic error handling.
 * Contains single public api method accept() which accepts from server client's address and message;
 */
public class MessageHandler {
    private static final String DELIMITER = " ";
    private static final String NEW_LINE = System.lineSeparator();
    private static final String NICK_CHANGED_MESSAGE = "'s nickname was changed to ";
    private static final String SERVER_NAME = "Server";
    private static final String WRONG_MESSAGE = "Unrecognized message" + NEW_LINE;

    private static final int COMMAND_INDEX = 0;
    private static final int BODY_INDEX = 1;

    private final UserHandler userHandler;
    private final ServerCore server;
    private final HistoryLogger historyLogger;
    private final MessageFormatter messageFormatter;

    public MessageHandler(ServerCore server) {
        this.server = server;
        this.userHandler = new UserHandler();
        this.historyLogger = new HistoryLogger(new FileHandler());
        this.messageFormatter = new MessageFormatter();
    }

    /**
     * Checks for basic errors and pass incoming data to further parsing and processing
     * @param address - ip address and port of a user who sent message
     * @param message - message from user, containing commands and or message body for chat
     * @throws ServerException
     */
    public void accept(String address, String message) throws ServerException {
        userHandler.accept(address);

        if (address == null) throw new ServerException("Null user id received");
        if (message == null) throw new ServerException("Null message received");
        if (address.isEmpty()) throw new ServerException("Empty id received");
        if (message.isEmpty()) throw new ServerException("Empty message received");

        parse(address, message);
    }

    private void parse(String address, String message) {
        String[] parsed = message.split(DELIMITER);
        String command = parsed[COMMAND_INDEX];
        String body = "";

        if ( parsed.length > BODY_INDEX) {
            body = parseBody(parsed);
        }
        String id = userHandler.getNameById(address);

        if (command.equals(Prefix.SEND.value)) {
            historyLogger.saveHistory(composeMessage(id, body));
            server.sendAll(composeMessage(id, body));
        } else if (command.equals(Prefix.CHID.value)) {
            userHandler.changeUsername(address, body);
            String nickChanged = id + NICK_CHANGED_MESSAGE + body + NEW_LINE;
            historyLogger.saveHistory(nickChanged);
            server.sendAll(nickChanged);
        } else if (command.equals(Prefix.HIST.value)) {
            server.sendToUser(address, composeMessage(SERVER_NAME, historyLogger.readHistory()));
        } else if (command.equals(Prefix.CHROOM.value)) {
            /////
            /////
        } else {
            String error = composeMessage(SERVER_NAME, WRONG_MESSAGE);
            historyLogger.saveHistory(error);
            server.sendToUser(address, error);
        }
    }

    private String parseBody(String[] parsed) {
        StringJoiner joiner = new StringJoiner(DELIMITER);
        for (int i = 1; i < parsed.length; i++) {
            joiner.add(parsed[i]);
        }
        return joiner.toString();
    }

    private String composeMessage(String id, String message) {
        return messageFormatter.format(id, message);
    }
}
