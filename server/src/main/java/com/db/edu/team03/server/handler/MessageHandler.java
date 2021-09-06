package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.core.Server;
import com.db.edu.team03.server.exception.ServerException;
import java.time.LocalTime;

public class MessageHandler implements Handler {

    private static final String DELIMITER = " ";
    private static final String WRONG_MESSAGE_FORMAT_ERROR_MESSAGE = "Unknown message received";
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
    public void accept(String id, String message) throws ServerException {
        System.out.println("id: " + id + ";  " + " message: " + message);
        userHandler.accept(id);
//        parse(id, message);
        try {
            if (id == null) throw new NullPointerException("Null user id received");
            if (message == null) throw new NullPointerException("Null message received");
            if (id.isEmpty()) throw new IllegalArgumentException("Empty id received");
            if (message.isEmpty()) throw new IllegalArgumentException("Empty message received");
        } catch (NullPointerException npe) {
            throw new ServerException(npe.getMessage(), npe);
        } catch (IllegalArgumentException iae) {
            throw new ServerException(iae.getMessage(), iae);
        }

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
                server.sendToUser(id, WRONG_MESSAGE_FORMAT_ERROR_MESSAGE);
                break;
        }
    }

    private String composeMessage(String id, String message) {
        String time = LocalTime.now().toString();
        return "[" + time + "]" + " " + id + ": " + message;
    }
}
