package com.db.edu.team03;

import com.db.edu.team03.server.core.ServerCore;
import com.db.edu.team03.server.handler.*;

public class ServerApplication {

    public static void main(String[] args) {
        ServerCore server = new ServerCore();

        MessageHandler messageHandler = new MessageHandler(server);
        server.setMessageHandler(messageHandler);
        server.listenPort();
    }
}
