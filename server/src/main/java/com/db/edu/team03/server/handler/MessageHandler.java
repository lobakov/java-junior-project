package com.db.edu.team03.server.handler;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.exception.ServerException;
import java.time.LocalTime;

public class MessageHandler implements Handler {
    private static final String SERVER_NAME = "Server";

    private static final int COMMAND_INDEX = 0;
    private static final int MESSAGE_INDEX = 1;

    private final UserHandler userHandler;
    private final ServerCore server;
    private final HistoryLogger historyLogger;


    public MessageHandler(ServerCore server, UserHandler userHandler, HistoryLogger historyLogger) {
        this.server = server;
        this.userHandler = userHandler;
        this.historyLogger = historyLogger;
    }

    @Override
    public void accept(String id, String message) throws ServerException {
        System.out.println("id: " + id + ";  " + " message: " + message);
        userHandler.accept(id);
//        parse(id, message);

        if (id == null) throw new ServerException("Null user id received");
        if (message == null) throw new ServerException("Null message received");
        if (id.isEmpty()) throw new ServerException("Empty id received");
        if (message.isEmpty()) throw new ServerException("Empty message received");

        parse(id, message);
    }

    private void parse(String id, String message) {
        String[] parsed = message.split(" ");
        String command = parsed[COMMAND_INDEX];
        String body = parsed[MESSAGE_INDEX];

        if(command.equals(Prefix.SEND.value)){
            historyLogger.saveHistory(composeMessage(id, message));
            server.sendAll(composeMessage(id, message));
        }else if(command.equals(Prefix.CHID.value)){
            userHandler.changeUsername(id, body);
        }else if(command.equals(Prefix.HIST.value)){
            server.sendToUser(id, composeMessage(SERVER_NAME, historyLogger.readHistory()));
        }else{
            server.sendToUser(id, "Message is wrong");
        }
    }

    private String composeMessage(String id, String message) {
        String time = LocalTime.now().toString();

        return String.format("[%s] %s: %s", time, id, message);
    }
}
