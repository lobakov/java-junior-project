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
    private static final String NICK_CHANGED_MESSAGE = "'s nickname was changed to ";
    private static final String SERVER_NAME = "Server";
    private static final String WRONG_MESSAGE = "Unrecognized message";

    private final UserHandler userHandler;
    private final ServerCore server;
    private final HistoryLogger historyLogger;
    private final MessageFormatter messageFormatter;

    public MessageHandler(ServerCore server, HistoryLogger historyLogger, UserHandler userHandler, MessageFormatter messageFormatter) {
        this.server = server;
        this.userHandler = userHandler;
        this.historyLogger = historyLogger;
        this.messageFormatter = messageFormatter;
    }

    /**
     * Checks for basic errors and pass incoming data to further parsing and processing
     *
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
        Prefix prefix = parsePrefix(message);
        String body = parseBody(message);

        String name = userHandler.getNameById(address);

        switch (prefix) {
            case SEND:
                sendMessage(name, body);
                break;
            case CHID:
                changeName(address, name, body);
                break;
            case HIST:
                sendHistory(address);
                break;
            default:
                sendWrongMessageError(address);
                break;
        }
    }

    public void removeUserNickname(String adress) {
        userHandler.removeUserByAddress(adress);
    }

    private void sendWrongMessageError(String address) {
        String error = messageFormatter.format(SERVER_NAME, WRONG_MESSAGE);

        historyLogger.saveHistory(error);
        server.sendToUser(address, error);
    }

    private void sendHistory(String address) {
        server.sendToUser(address, historyLogger.readHistory());
    }

    private void sendMessage(String name, String body) {
        String composeMessage = messageFormatter.format(name, body);

        server.sendAll(composeMessage);
        historyLogger.saveHistory(composeMessage);
    }

    private void changeName(String address, String name, String body) {
        String resultOfSetName = userHandler.changeUsername(address, body);

        if (resultOfSetName.equals(body)) {
            String nickChanged = name + NICK_CHANGED_MESSAGE + body;
            server.sendAll(nickChanged);
            historyLogger.saveHistory(nickChanged);
        } else {
            server.sendToUser(address, resultOfSetName);
        }
    }

    private Prefix parsePrefix(String message){
        return Prefix.commandToPrefix(message.split(DELIMITER)[0]);
    }

    private String parseBody(String message) {
        String[] parsed = message.split(DELIMITER);

        if (parsed.length > 1) {
            StringJoiner joiner = new StringJoiner(DELIMITER);
            for (int i = 1; i < parsed.length; i++) {
                joiner.add(parsed[i]);
            }
            return joiner.toString();
        }
        return "";
    }
}
