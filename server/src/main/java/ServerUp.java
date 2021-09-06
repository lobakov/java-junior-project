import com.db.edu.team03.server.handler.Handler;
import com.db.edu.team03.server.handler.MessageHandler;
import com.db.edu.team03.server.handler.ServerCore;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerUp {
    public static void main(String[] args) {
        ServerCore serverCore = new ServerCore();
        serverCore.listenPort();
    }
}
